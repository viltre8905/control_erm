package com.verynet.gcint.api.aop;

import com.verynet.gcint.api.annotation.Logging;
import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.JavaUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 17/05/2016.
 */
public class LoggingAdvice implements MethodInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger("com.verynet.gcint.api");
    private static final String[] SETTER_METHOD_PREFIXES = {"save", "add", "void", "delete"};

    /**
     * This method prints out debug statements for getters and info statements for everything else
     * ("setters"). If debugging is turned on, execution time for each method is printed as well.
     * This method is called for every method in the Class/Service that it is wrapped around.
     *
     * @see MethodInterceptor#invoke(MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String name = method.getName();
        // decide what type of logging we're doing with the current method and loglevel
        boolean isSetterTypeOfMethod = JavaUtil.stringStartsWith(name, SETTER_METHOD_PREFIXES);
        boolean logGetter = !isSetterTypeOfMethod && logger.isDebugEnabled();
        boolean logSetter = isSetterTypeOfMethod && logger.isInfoEnabled();
        // used for the execution time calculations
        long startTime = System.currentTimeMillis();
        // check if this method has the logging annotation on it
        Logging loggingAnnotation = null;
        if (logGetter || logSetter) {
            loggingAnnotation = method.getAnnotation(Logging.class);
            if (loggingAnnotation != null && loggingAnnotation.ignore()) {
                logGetter = false;
                logSetter = false;
            }
        }
        if (logGetter || logSetter) {
            StringBuilder output = new StringBuilder();
            output.append("In method ").append(method.getDeclaringClass().getSimpleName()).append(".").append(name);
            // print the argument values unless we're ignoring all
            if (loggingAnnotation == null || loggingAnnotation.ignoreAllArgumentValues() == false) {
                Class<?>[] types = method.getParameterTypes();
                Object[] values = invocation.getArguments();

                // change the annotation array of indexes to a list of indexes to ignore
                List<Integer> argsToIgnore = new ArrayList<Integer>();
                if (loggingAnnotation != null && loggingAnnotation.ignoredArgumentIndexes().length > 0) {
                    for (int argIndexToIgnore : loggingAnnotation.ignoredArgumentIndexes()) {
                        argsToIgnore.add(argIndexToIgnore);
                    }
                }
                // loop over and print out each argument value
                output.append(". Arguments: ");
                for (int i = 0; i < types.length; i++) {
                    output.append(types[i].getSimpleName()).append("=");
                    // if there is an annotation to skip this, print out a bogus string.
                    if (argsToIgnore.contains(i)) {
                        output.append("<Arg value ignored>");
                    } else {
                        output.append(values[i]);
                    }
                    if (i < types.length - 1) {
                        output.append(", ");
                    }
                }
            }
            // print the string as either debug or info
            if (logGetter) {
                logger.debug(output.toString());
            } else if (logSetter) {
                logger.info(output.toString());
            }
        }
        try {
            return invocation.proceed();
        } catch (Exception e) {
            if (logGetter || logSetter) {
                String username;
                User user = Context.getAuthenticatedUser();
                if (user == null) {
                    username = "Guest (Not logged in)";
                } else {
                    username = user.getUserName();
                }
                logger.error(String.format(
                        "An error occurred while executing this method.\nCurrent user: %s\nError message: %s", username, e
                                .getMessage()), e);
            }
            throw e;
        } finally {
            if (logGetter || logSetter) {
                StringBuilder output = new StringBuilder();
                output.append("Exiting method ").append(name);
                // only append execution time info if we're in debug mode
                if (logger.isDebugEnabled()) {
                    output.append(". execution time: " + (System.currentTimeMillis() - startTime)).append(" ms");
                }
                // print the string as either debug or info
                if (logGetter) {
                    logger.debug(output.toString());
                } else if (logSetter) {
                    logger.info(output.toString());
                }
            }
        }
    }
}
