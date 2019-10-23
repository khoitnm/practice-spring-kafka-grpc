Supports 3 cases:
+ SUccess

+ Error in Deserialization: 
Input from Producer with realName is "DeErr": http://localhost:8080/send?realName=DeErr
If we don't carefully handle this case, it could case endless loop in Consumer. So we need to test it.

+ Error in Listener:
Input from Producer with realName is blank: http://localhost:8080/send?realName=
