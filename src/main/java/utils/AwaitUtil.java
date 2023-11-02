package utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.hamcrest.Matcher;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

@Slf4j
@UtilityClass
public class AwaitUtil {
    public static final Duration MAX_TIME = Duration.ofSeconds(20);
    private static final Duration POLL_INTERVAL = Duration.ofMillis(500);
    private static final String POLL_MESSAGE = "Waiting for condition to be ready";

    private static <T> T awaitBase(Duration duration, Duration pollInterval,
                                   final Callable<T> supplier, final Matcher<? super T> matcher, String alias) {
        return Awaitility.await(alias)
                .pollInSameThread()
                .atMost(duration)
                .pollInterval(pollInterval)
                .until(supplier, matcher);
    }

    private static <T> T awaitBase(Duration duration, final Callable<T> supplier,
                                   final Predicate<? super T> predicate, final String alias) {
        return Awaitility.await(alias)
                .pollInSameThread()
                .atMost(duration)
                .pollInterval(POLL_INTERVAL)
                .until(supplier, predicate);
    }

    public static <T> T await(Duration duration, Duration pollInterval,
                              final Callable<T> supplier, final Matcher<? super T> matcher) {
        return awaitBase(duration, pollInterval, supplier, matcher, POLL_MESSAGE);
    }

    public static <T> T await(Duration duration, Duration pollInterval,
                              final Callable<T> supplier, final Matcher<? super T> matcher, String message) {
        return awaitBase(duration, pollInterval, supplier, matcher, message);
    }

    public static <T> T await(Duration duration, final Callable<T> supplier, final Matcher<? super T> matcher) {
        return awaitBase(duration, POLL_INTERVAL, supplier, matcher, POLL_MESSAGE);
    }

    public static <T> T await(Duration duration, final Callable<T> supplier, final Matcher<? super T> matcher, String message) {
        return awaitBase(duration, POLL_INTERVAL, supplier, matcher, message);
    }

    public static <T> T await(final Callable<T> supplier, final Matcher<? super T> matcher) {
        return awaitBase(MAX_TIME, POLL_INTERVAL, supplier, matcher, POLL_MESSAGE);
    }

    public static <T> void awaitSafe(final Callable<T> supplier, Duration duration, final Matcher<? super T> matcher) {
        try {
            awaitBase(duration, POLL_INTERVAL, supplier, matcher, POLL_MESSAGE);
        } catch (Exception | AssertionError ex) {
            log.debug("Condition failed!");
        }
    }

    public static <T> boolean awaitSafe(Duration duration, Duration pollInterval, final Callable<T> supplier, final Matcher<? super T> matcher) {
        try {
            awaitBase(duration, pollInterval, supplier, matcher, POLL_MESSAGE);
            return true;
        } catch (Exception | AssertionError ex) {
            log.debug("Condition failed!");
            return false;
        }
    }

    public static <T> T await(final Callable<T> supplier, final Predicate<? super T> predicate) {
        return awaitBase(MAX_TIME, supplier, predicate, POLL_MESSAGE);
    }

}
