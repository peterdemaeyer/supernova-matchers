package su.pernova.matchers.object;

import su.pernova.matchers.Matcher;
import org.junit.jupiter.api.Test;

import static su.pernova.matchers.AbstractMatcherTest.*;
import static su.pernova.matchers.core.EqualsMatcher.equalTo;
import static su.pernova.matchers.object.HasToString.hasToString;

public final class HasToStringTest {
    private static final String TO_STRING_RESULT = "toString result";
    private static final Object TEST_OBJECT = new Object() {
        @Override
        public String toString() {
            return TO_STRING_RESULT;
        }
    };

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasToString(equalTo("irrelevant"));
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }
    
    @Test public void
    matchesWhenUtilisingANestedMatcher() {
        final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));

        assertMatches(matcher, TEST_OBJECT);
        assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    matchesWhenUsingShortcutForHasToStringEqualTo() {
        final Matcher<Object> matcher = hasToString(TO_STRING_RESULT);
        
        assertMatches(matcher, TEST_OBJECT);
        assertDoesNotMatch(matcher, new Object());
    }

    @Test public void
    describesItself() {
        final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));
        assertDescription("with toString() \"toString result\"", matcher);
    }

    @Test public void
    describesAMismatch() {
        final Matcher<Object> matcher = hasToString(equalTo(TO_STRING_RESULT));
        String expectedMismatchString = "toString() was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, matcher, "Cheese");
    }
}
