package su.pernova.matchers.text;

import su.pernova.matchers.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static su.pernova.matchers.AbstractMatcherTest.*;
import static su.pernova.matchers.text.IsEqualIgnoringCase.equalToIgnoringCase;

public final class EqualsMatcherIgnoringCaseTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<String> matcher = equalToIgnoringCase("irrelevant");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    ignoresCaseOfCharsInString() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        
        assertMatches(matcher, "HELLO");
        assertMatches(matcher, "hello");
        assertMatches(matcher, "HelLo");
        assertDoesNotMatch(matcher, "bye");
    }

    @Test public void 
    mismatchesIfAdditionalWhitespaceIsPresent() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        
        assertDoesNotMatch(matcher, "hello ");
        assertDoesNotMatch(matcher, " hello");
    }

    @Test public void 
    mismatchesNull() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        
        assertDoesNotMatch(matcher, null);
    }

    @Test public void
    canOnlyBeConstructedAboutANonNullString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> equalToIgnoringCase(null));
    }


    @Test public void
    describesItself() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        assertDescription("a string equal to \"heLLo\" ignoring case", matcher);
    }

    @Test public void
    describesAMismatch() {
        final Matcher<String> matcher = equalToIgnoringCase("heLLo");
        String expectedMismatchString = "was \"Cheese\"";
        assertMismatchDescription(expectedMismatchString, matcher, "Cheese");
    }
}