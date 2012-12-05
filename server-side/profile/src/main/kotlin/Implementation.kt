package com.github.marschall.kotlin.profile.implementation

import javax.ejb.Singleton
import javax.jws.WebMethod
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.ParameterStyle
import org.jboss.ws.api.annotation.WebContext

/**
 * This EJB will be available as a SOAP web service.
 *
 * <p>The service will be available under
 * <a href="http://localhost:8080/profile/webservice">http://localhost:8080/profile/webservice</a>
 * and the generated WSDL can be found under
 * <a href="http://localhost:8080/profile/webservice?wsdl">http://localhost:8080/profile/webservice?wsdl</a>
 * </p>
 */
Singleton
WebService(
    name = "Profile",
    targetNamespace = "http://www.acme.com/profile",
    serviceName = "ProfileService")
SOAPBinding(parameterStyle = ParameterStyle.BARE)
WebContext(contextRoot = "profile", urlPattern = "/webservice")
// remember Session bean implementation class MUST be public, not abstract and not final
open class ProfileBean {

    /**
     * A simple identity function just returning the argument.
     *
     * <p>This method solely exists to check for encoding errors like
     * <a href="https://issues.jboss.org/browse/JBWS-3213">JBWS-3213</a>.</p>
     */
    WebMethod
    // remember Session bean methods MUST be public, not abstract and not final
    open fun identity(s: String): String {
        return s
    }

}