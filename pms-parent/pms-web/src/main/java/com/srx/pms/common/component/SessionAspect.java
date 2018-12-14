package com.srx.pms.common.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
//@Component
public class SessionAspect {
	@Pointcut("@annotation(com.srx.pms.common.annotation.SessionUser)")
    public void serviceAspect() {  
    } 
	@Before("serviceAspect()")  
    public void doBefore(ProceedingJoinPoint pjp){  
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
//                .getRequestAttributes()).getRequest();  
//        HttpSession session = request.getSession();  
//        // 读取session中的用户  
////        User user = (User) session.getAttribute(Contant.Session.KEY_SESSION_USER); 
//        try {
//			pjp.proceed(null);
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
