package com.fit.security;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;

public class JettyHttpFirewall implements HttpFirewall {

	private boolean allowUrlEncodedSlash;

	@Override
	public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
		FirewalledRequest fwr = new RequestWrapper(request);

		if (!isNormalized(fwr.getServletPath()) || !isNormalized(fwr.getPathInfo())) {
			throw new RequestRejectedException("Un-normalized paths are not supported: " + fwr.getServletPath()
					+ (fwr.getPathInfo() != null ? fwr.getPathInfo() : ""));
		}

		String requestURI = fwr.getRequestURI();
		if (containsInvalidUrlEncodedSlash(requestURI)) {
			throw new RequestRejectedException("The requestURI cannot contain encoded slash. Got " + requestURI);
		}

		return fwr;
	}

	public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
		return new FirewalledResponse(response);
	}

	public void setAllowUrlEncodedSlash(boolean allowUrlEncodedSlash) {
		this.allowUrlEncodedSlash = allowUrlEncodedSlash;
	}

	private boolean containsInvalidUrlEncodedSlash(String uri) {
		if (this.allowUrlEncodedSlash || uri == null) {
			return false;
		}

		if (uri.contains("%2f") || uri.contains("%2F")) {
			return true;
		}

		return false;
	}

	private boolean isNormalized(String path) {
		if (path == null) {
			return true;
		}

		for (int j = path.length(); j > 0;) {
			int i = path.lastIndexOf('/', j - 1);
			int gap = j - i;

			if (gap == 2 && path.charAt(i + 1) == '.') {
				// ".", "/./" or "/."
				return false;
			} else if (gap == 3 && path.charAt(i + 1) == '.' && path.charAt(i + 2) == '.') {
				return false;
			}

			j = i;
		}
		return true;
	}

	class FirewalledResponse extends HttpServletResponseWrapper {
		private final Pattern CR_OR_LF = Pattern.compile("\\r|\\n");

		public FirewalledResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			if (CR_OR_LF.matcher(location).find()) {
				throw new IllegalArgumentException("Invalid characters (CR/LF) in redirect location");
			}
			super.sendRedirect(location);
		}
	}

	final class RequestWrapper extends FirewalledRequest {
		private final String strippedServletPath;
		private final String strippedPathInfo;
		private boolean stripPaths = true;

		public RequestWrapper(HttpServletRequest request) {
			super(request);
			strippedServletPath = strip(request.getServletPath());
			String pathInfo = strip(request.getPathInfo());
			if (pathInfo != null && pathInfo.length() == 0) {
				pathInfo = null;
			}
			strippedPathInfo = pathInfo;
		}

		private String strip(String path) {
			if (path == null) {
				return null;
			}

			int scIndex = path.indexOf(';');

			if (scIndex < 0) {
				int doubleSlashIndex = path.indexOf("//");
				// 有可能没有'//',不用去除
				if (doubleSlashIndex < 0) {
					return path;
				}
			}

			StringTokenizer st = new StringTokenizer(path, "/");
			StringBuilder stripped = new StringBuilder(path.length());

			if (path.charAt(0) == '/') {
				stripped.append('/');
			}

			while (st.hasMoreTokens()) {
				String segment = st.nextToken();
				scIndex = segment.indexOf(';');

				if (scIndex >= 0) {
					segment = segment.substring(0, scIndex);
				}
				stripped.append(segment).append('/');
			}
			// 移除尾部的斜杠如果原始路径没有
			if (path.charAt(path.length() - 1) != '/') {
				stripped.deleteCharAt(stripped.length() - 1);
			}

			return stripped.toString();
		}

		@Override
		public String getPathInfo() {
			return stripPaths ? strippedPathInfo : super.getPathInfo();
		}

		@Override
		public String getServletPath() {
			return stripPaths ? strippedServletPath : super.getServletPath();
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return this.stripPaths ? new FirewalledRequestAwareRequestDispatcher(path) : super.getRequestDispatcher(path);
		}

		public void reset() {
			this.stripPaths = false;
		}

		private class FirewalledRequestAwareRequestDispatcher implements RequestDispatcher {
			private final String path;

			public FirewalledRequestAwareRequestDispatcher(String path) {
				this.path = path;
			}

			public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
				reset();
				getDelegateDispatcher().forward(request, response);
			}

			public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
				getDelegateDispatcher().include(request, response);
			}

			private RequestDispatcher getDelegateDispatcher() {
				return RequestWrapper.super.getRequestDispatcher(path);
			}
		}
	}
}
