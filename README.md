# Demo CVE-2023-50164

Install as follows:

```
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

Run as follows:
- `mvn clean jetty:run` to build the project and start the Struts application in Jetty at http://localhost:9999/foo 
- `python exploit` to demo the attacks with the default values, where the payload is uploaded to `src/main/webapp` and served by Jetty 
- Use `--proxy http://...` to use a proxy like OWASP ZAP
- Use `--legit` to make a legit request (the 2nd malicious part of the multipart request is omitted), where the payload is uploaded to the intended upload folder `uploads`

Debug as follows:
- Set breakpoints in the setter methods of `Upload.java`
- Run `mvnDebug jetty:run`
- Attach the debugger
- Run `python exploit` and observe the duplicate invocation of `setUploadFileName` (once with the sanitized value from the 1st part of the multipart request, then with the value from the 2nd part)


Forked from https://github.com/jakabakos/CVE-2023-50164-Apache-Struts-RCE
