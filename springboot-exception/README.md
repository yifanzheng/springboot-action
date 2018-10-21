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
@ControllerAdvice(basePackages = "com.example.exception.web")
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
### 异常捕获的相关注解
**`@ControllerAdvice`** 是 controller 的一个辅助类，最常用的就是作为全局异常处理的切面类，采用AOP技术，可以指定扫描范围。

**`@ControllerAdvice`** 约定了几种可行的返回值：
- 返回 String，表示跳到某个页面
- 返回 modelAndView 表示将处理结果数据传递到结果页面
- 返回 model + @ResponseBody 表示返回json格式

**`@ExceptionHandler`** 定义方法拦截的异常类型，只有拦截到属于该异常范围内的异常才触发该方法执行。

