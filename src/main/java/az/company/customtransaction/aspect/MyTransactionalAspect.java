package az.company.customtransaction.aspect;

import az.company.customtransaction.annotation.MyTransactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;

@Aspect
@Component
public class MyTransactionalAspect {

    private final PlatformTransactionManager transactionManager;

    public MyTransactionalAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(az.company.customtransaction.annotation.MyTransactional)")
    public Object invokeTransactionalMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        MyTransactional annotation = method.getAnnotation(MyTransactional.class);

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(annotation.isolation().value());
        definition.setPropagationBehavior(annotation.propagation().value());

        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            return result;
        } catch (RuntimeException e) { // Handle unchecked exceptions
            transactionManager.rollback(status);
            throw e;
        } catch (Exception e) { // Handle checked exceptions
            transactionManager.commit(status); // Commit on checked exceptions
            throw e;
        }
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

}
