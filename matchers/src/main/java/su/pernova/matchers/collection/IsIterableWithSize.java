package su.pernova.matchers.collection;

import su.pernova.matchers.FeatureMatcher;
import su.pernova.matchers.Matcher;

import java.util.Iterator;

import static su.pernova.matchers.core.IsEqual.equalTo;

public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {

    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }
    

    @Override
    protected Integer featureValueOf(Iterable<E> actual) {
      int size = 0;
      for (Iterator<E> iterator = actual.iterator(); iterator.hasNext(); iterator.next()) {
        size++;
      }
      return size;
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     * 
     * @param sizeMatcher
     *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<E>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     * 
     * @param size
     *     the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return iterableWithSize(equalTo(size));
    }
}