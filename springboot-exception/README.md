# springboot 全局异常捕获
### web页面跳转
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_VIEW="errors";

    @ExceptionHandler(value = ArithmeticException.class)
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception",e);
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.setViewName(ERROR_VIEW);
        return modelAndView;
    }
}
```
### ajax形式跳转
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NullPointerException.class)
    public JsonResponse ajaxErrorHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        return JsonResponse.errorException(e.getMessage());
    }
}
```

### 同时兼容web和ajax跳转
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_VIEW="errors";

    @ExceptionHandler(Exception.class)
    public Object errorHandler(HttpServletRequest request,HttpServletResponse response,Exception e){
        e.printStackTrace();
        if(isAjax(request)){
            return JsonResponse.errorException(e.getMessage());
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("exception",e);
            modelAndView.addObject("url",request.getRequestURL());
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With")!=null
                && Objects.equals(request.getHeader("X-Requested-With").toString(),"XMLHttpRequest"));
    }

}
```