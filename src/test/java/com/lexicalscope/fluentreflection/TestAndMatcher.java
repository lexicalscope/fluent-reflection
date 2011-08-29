package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ListBuilder.list;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.fluentreflection.AndMatcher;
import com.lexicalscope.fluentreflection.testhelpers.JUnitRuleMockery;

public class TestAndMatcher {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Matcher<String> matcherA = containsString("a");
	private final Matcher<String> matcherB = containsString("b");

	private final AndMatcher<String> matcher =
			AndMatcher.andOf(list(matcherA).add(matcherB).$());

	@Test
	public void ifOnlyOneMatcherMatchesThenFalse() throws Exception {
		assertThat("a", not(matcher));
		assertThat("b", not(matcher));
	}

	@Test
	public void ifBothMatcherMatchesThenTrue() throws Exception {
		assertThat("ab", matcher);
	}

	@Test
	public void descriptionDescribesBothMatchers() throws Exception {
		final Description description = context.mock(Description.class);

		context.checking(new Expectations() {
			{
				oneOf(description).appendDescriptionOf(matcherA);
				oneOf(description).appendText(" and ");
				oneOf(description).appendDescriptionOf(matcherB);
			}
		});

		matcher.describeTo(description);
	}

	@Test
	public void descriptionWithSingleMatcherIsSensible() throws Exception {
		final Description description = context.mock(Description.class);

		context.checking(new Expectations() {
			{
				oneOf(description).appendDescriptionOf(matcherA);
			}
		});

		AndMatcher.andOf(list(matcherA).$()).describeTo(description);
	}
}
