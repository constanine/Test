package secrecy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class SecrecyFilter implements Filter{
	  String nofilter;
	  String[] nofilterFiles;
	  String sendRedirect;
	  
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		    throws IOException, ServletException {
		    HttpServletRequest req = (HttpServletRequest)request;
		    HttpServletResponse res = (HttpServletResponse)response;
		    HttpSession session = req.getSession();
		    String path = req.getServletPath();
		    if (isInArray(path, this.nofilterFiles)) {
		      chain.doFilter(request, response);
		    }
		    else if (session.getAttribute("player") != null)
		      chain.doFilter(request, response);
		    else
		      res.sendRedirect("/trebuchet/login.jsp");
    }

	private boolean isInArray(String path, String[] nofilterFiles){
	    for (int i = 0; i < nofilterFiles.length; i++) {
	      String nofilterFile = nofilterFiles[i];
	      if (nofilterFile.endsWith("*")) {
	        boolean itmatch = isMatchwithfolder(nofilterFile, path);
	        if (itmatch) {
	          return true;
	        }
	      }
	      else if (nofilterFile.equals(path)) {
	        return true;
	      }
	    }

	    return false;
	}

    private boolean isMatchwithfolder(String nofilterFile, String path) {
	    String[] authfolders = nofilterFile.split("/");
	    String[] reqfolders = path.split("/");
	    String authfolder = "";
	    String reqfolder = "";
	    for (int i = 0; i < authfolders.length - 1; i++) {
	      authfolder = authfolder + authfolders[i];
	    }

	    for (int i = 0; i < reqfolders.length - 1; i++) {
	      reqfolder = reqfolder + reqfolders[i];
	      if (authfolder.equals(reqfolder)) {
	    	  return true;
	      }
	    }

	    return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.nofilter = config.getInitParameter("nofilter");
	    this.nofilterFiles = this.nofilter.split(",");		
	}

}
