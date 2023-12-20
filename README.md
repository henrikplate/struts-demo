# Demo CVE-2023-50164

Install as follows:

```
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

Run as follows:
- `mvn clean jetty:run` to build the project and start the Struts app in Jetty at http://localhost:9999/foo 
- Run `python exploit` to demo the attack with default values, where the payload is uploaded to `src/main/webapp` and served by Jetty 
- Use `--proxy http://localhost:8080` to use a proxy like [OWASP ZAP](https://www.zaproxy.org/)
- Use `--legit` to make a legit request (the 2nd malicious part of the multipart request is omitted), where the payload is uploaded to the intended upload folder `uploads`

Debug as follows:
- Set breakpoints in the setter method `setUploadFileName(String)` of `Upload.java`
- Run `mvnDebug jetty:run`
- Attach the debugger
- Run `python exploit` and observe the duplicate invocation of `setUploadFileName(String)` (once with the sanitized value from the 1st part of the multipart request, then with the value from the 2nd part)
- Other useful breakpoints are
    - `org.apache.struts2.interceptor.FileUploadInterceptor.intercept(ActionInvocation)`, where the interception of all file uploads happens, incl. the construction of parameter names
    - `org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest.getCanonicalName(String)`, where the 1st part of the multipart request is properly sanitized
    - `com.opensymphony.xwork2.interceptor.ParametersInterceptor.setParameters(Object, ValueStack, Map<String, Object>)`, where all parameters from the multipart request end up in one map
    - `ognl.OgnlRuntime._getSetMethod(OgnlContext, targetClass, String)`, where properties that only differ in the case resolve to the same setter method `setUploadFileName`

Adapted from https://github.com/jakabakos/CVE-2023-50164-Apache-Struts-RCE.

References:
- https://www.vicarius.io/vsociety/posts/apache-struts-rce-cve-2023-50164
