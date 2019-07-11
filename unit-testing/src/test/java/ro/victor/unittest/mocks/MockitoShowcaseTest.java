package ro.victor.unittest.mocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class MockitoShowcaseTest {
	
	public interface Dependency { // or concrete class
		public String someMethod(String string);
	}

	@Mock
	private Dependency someMock;
	
	@Mock
	private List<String> mockedList;

	@Test
	public void verifySomeBehaviour() {
		
		System.out.println(mockedList.getClass());
		{
			// using mock object
			mockedList.add("one");
			mockedList.clear();
		}

		// verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

	@Test
	public void someStubbing() {
		// You can mock concrete classes, not only interfaces
		LinkedList<String> mock = mock(LinkedList.class);

		// stubbing
		when(mock.get(0)).thenReturn("first");

		{
			// following prints "first"
			System.out.println(mock.get(0));

			// following prints "null" because get(999) was not stubbed
			System.out.println(mock.get(999));
		}

		// Although it is possible to verify a stubbed invocation, usually it's just redundant
		// If your code cares what get(0) returns then something else would break anyway
		// If your code doesn't care what get(0) returns then it should not be stubbed.
		verify(mock).get(0);
	}

	// - By default, for all methods that return value, mock returns null, an
	// empty collection or appropriate primitive/primitive wrapper value (e.g:
	// 0, false, ... for int/Integer, boolean/Boolean, ...).

	// - Stubbing can be overridden: for example common stubbing can go to
	// fixture setup but the test methods can override it. Please note that
	// overridding stubbing is a potential code smell that points out too much
	// stubbing

	// - Once stubbed, the method will always return stubbed value regardless of
	// how many times it is called.

	// - Last stubbing takes precedence - when you stubbed the same method with
	// the same arguments many times.

	// expect an exception to be thrown by the test
	@Test(expected = RuntimeException.class)
	public void someStubbingToThrowError() {
		// mock created as a test field by the JUnit Runner

		// stubbing
		when(mockedList.get(anyInt())).thenThrow(new RuntimeException());

		{
			// following throws runtime exception
			System.out.println(mockedList.get(2));
		}
		
		//		Read more about other methods:
		//			doThrow(Throwable)
		//			doAnswer(Answer)
		//			doNothing()
		//			doReturn(Object) 
	}

	// !! for automatic static import, add org.mockito.Matchers to your Preferences>Java>Editor>Content Assist>Favorites !! 
	@Test
	public void argumentMatchers() {
		// stubbing using built-in anyInt() argument matcher
		when(mockedList.get(anyInt())).thenReturn("element");

		// (advanced) stubbing using Hamcrest
//		when(mockedList.contains(argThat(new IsEqual<String>("111")))).thenReturn(true);

		System.out.println(mockedList.get(999)); // print "element"
		System.out.println("" + mockedList.contains("111")); // print "true"

		// you can also verify using an argument matcher
		verify(mockedList).get(anyInt());
		
		// Warning on argument matchers:
		// !!! If you are using argument matchers, all arguments have to be provided by matchers.
		// E.g: (example shows verification but the same applies to stubbing):

		//- verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
		// above is correct - eq() is also an argument matcher

		//- verify(mock).someMethod(anyInt(), anyString(), "third argument");
		// above is incorrect - exception will be thrown because third argument is given without an argument matcher.
			  
	}

	@Test
	public void verifyNumberOfInvocations() {
		 mockedList.add("once");
		 
		 mockedList.add("twice");
		 mockedList.add("twice");
		 
		 mockedList.add("three times");
		 mockedList.add("three times");
		 mockedList.add("three times");
		 
		 //following two verifications work exactly the same - times(1) is used by default
		 verify(mockedList).add("once");
		 verify(mockedList, times(1)).add("once");
		 
		 //exact number of invocations verification
		 verify(mockedList, times(2)).add("twice");
		 verify(mockedList, times(3)).add("three times");
		 
		 //verification using never(). never() is an alias to times(0)
		 verify(mockedList, never()).add("never happened");
		 
		 //verification using atLeast()/atMost()
		 verify(mockedList, atLeastOnce()).add("three times");
		 verify(mockedList, atLeast(2)).add("three times");
		 verify(mockedList, atMost(5)).add("three times");
		 
		 // times(1) is the default. Therefore using times(1) explicitly can be omitted. 
	}

	@Test(expected = RuntimeException.class)
	public void voidMethodToThrowException() {
		doThrow(new RuntimeException()).when(mockedList).clear();

		// following throws RuntimeException:
		mockedList.clear();
	}

	@Test
	public void stubbingConsecutiveCalls() {
		// Sometimes we need to stub with different return value/exception for
		// the same method call. Typical use case could be mocking iterators.
		// Original version of Mockito did not have this feature to promote
		// simple mocking. For example, instead of iterators one could use
		// Iterable or simply collections. Those offer natural ways of stubbing
		// (e.g. using real collections). In rare scenarios stubbing consecutive
		// calls could be useful, though:

		when(someMock.someMethod("some arg"))
			.thenThrow(new RuntimeException())
			.thenReturn("foo");

		// First call: throws runtime exception:
		try {
			someMock.someMethod("some arg");
			fail();
		} catch (RuntimeException e) {
			// expected
		}

		// Second call: prints "foo"
		System.out.println(someMock.someMethod("some arg"));

		// Any consecutive call: prints "foo" as well (last stubbing wins).
		System.out.println(someMock.someMethod("some arg"));

		// Alternative, shorter version of consecutive stubbing:
		when(someMock.someMethod("some arg"))
			.thenReturn("one", "two", "three");
	}
	
	@Test
	public void stubbingWithCallbacks() {
		 when(someMock.someMethod(anyString())).thenAnswer(invocation -> {
		         Object[] args = invocation.getArguments();
		         Object mock = invocation.getMock();
		         return "called with arguments: " + Arrays.toString(args);// + Math.random();
		 });
		 
		 //Following prints "called with arguments: foo"
		 System.out.println(someMock.someMethod("foo"));
	}
	
	@Test
	public void capturingArgumentsForFurtherAssertions() {
		// Mockito verifies argument values in natural java style: by using an
		// equals() method. This is also the recommended way of matching
		// arguments because it makes tests clean & simple. In some situations
		// though, it is helpful to assert on certain arguments after the actual
		// verification. For example:

		someMock.someMethod("John");
		
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(someMock).someMethod(argument.capture());
		assertEquals("John", argument.getValue());
			 

		// Warning: it is recommended to use ArgumentCaptor with verification
		// but not with stubbing. Using ArgumentCaptor with stubbing may
		// decrease test readability because captor is created outside of assert
		// (aka verify or 'then') block. Also it may reduce defect localization
		// because if stubbed method was not called then no argument is
		// captured.

		// In a way ArgumentCaptor is related to custom argument matchers (see
		// javadoc for ArgumentMatcher class). Both techniques can be used for
		// making sure certain arguments where passed to mocks. However,
		// ArgumentCaptor may be a better fit if:
		// - custom argument matcher is not likely to be reused
		// - you just need it to assert on argument values to complete verification

		// Custom argument matchers via ArgumentMatcher are usually better for stubbing. 
	}
	
	
	// ------------------ advaced / dangerous features ------------------------
	@Test
	public void restMocks() {
		// Smart Mockito users hardly use this feature because they know it
		// could be a sign of poor tests. Normally, you don't need to reset your
		// mocks, just create new mocks for each test method.

		// Instead of reset() please consider writing simple, small and focused
		// test methods over lengthy, over-specified tests. First potential code
		// smell is reset() in the middle of the test method. This probably
		// means you're testing too much. Follow the whisper of your test
		// methods: "Please keep us small & focused on single behavior". There
		// are several threads about it on mockito mailing list.

		// The only reason we added reset() method is to make it possible to
		// work with container-injected mocks. See issue 55 (here) or FAQ
		// (here).

		// Don't harm yourself. reset() in the middle of the test method is a code smell (you're probably testing too much). 
		List<String> mock = mock(List.class);
		when(mock.size()).thenReturn(10);
		mock.add("1");

		reset(mock);
		// at this point the mock forgot any interactions & stubbing
	}
	
	@Test
	public void spyingRealObjects() {
		// You can create spies of real objects. When you use the spy then the
		// real methods are called (unless a method was stubbed).

		// Real spies should be used carefully and occasionally, for example
		// when dealing with legacy code.

		// Spying on real objects can be associated with "partial mocking"
		// concept. Before the release 1.8, Mockito spies were not real partial
		// mocks. The reason was we thought partial mock is a code smell. At
		// some point we found legitimate use cases for partial mocks (3rd party
		// interfaces, interim refactoring of legacy code, the full article is
		// here)

		List<String> list = new LinkedList<String>();
		List<String> spy = Mockito.spy(list);

		// optionally, you can stub out some methods:
		when(spy.size()).thenReturn(100);

		// using the spy calls real methods
		spy.add("one");
		spy.add("two");

		// prints "one" - the first element of a list
		System.out.println(spy.get(0));

		// size() method was stubbed - 100 is printed
		System.out.println(""  + spy.size());

		// optionally, you can verify
		verify(spy).add("one");
		verify(spy).add("two");

		// Important gotcha on spying real objects!
		// 1. Sometimes it's impossible to use when(Object) for stubbing spies.
		// Example:

		// List list = new LinkedList();
		// List spy = spy(list);
		//
		// //Impossible: real method is called so spy.get(0) throws
		// IndexOutOfBoundsException (the list is yet empty)
		// when(spy.get(0)).thenReturn("foo");
		//
		// //You have to use doReturn() for stubbing
		// doReturn("foo").when(spy).get(0);

		// 2. Watch out for final methods. Mockito doesn't mock final methods so
		// the bottom line is: when you spy on real objects + you try to stub a
		// final method = trouble. What will happen is the real method will be
		// called *on mock* but *not on the real instance* you passed to the
		// spy() method. Typically you may get a NullPointerException because
		// mock instances don't have fields initiated.
	}
	
	@Test
	public void partialMocks() {
		// Finally, after many internal debates & discussions on the mailing
		// list, partial mock support was added to Mockito. Previously we
		// considered partial mocks as code smells. However, we found a
		// legitimate use case for partial mocks - more reading: here

		// Before release 1.8 spy() was not producing real partial mocks and it
		// was confusing for some users. Read more about spying: here or in
		// javadoc for spy(Object) method.

		// you can enable partial mock capabilities selectively on mocks:
		LinkedList<String> mock = mock(LinkedList.class);
		
		// Be sure the real implementation is 'safe'.
		// If real implementation throws exceptions or depends on specific state
		// of the object then you're in trouble.
		when(mock.size()).thenCallRealMethod();

		// As usual you are going to read the partial mock warning: Object
		// oriented programming is more less tackling complexity by dividing the
		// complexity into separate, specific, SRPy objects. How does partial
		// mock fit into this paradigm? Well, it just doesn't... Partial mock
		// usually means that the complexity has been moved to a different
		// method on the same object. In most cases, this is not the way you
		// want to design your application.

		// However, there are rare cases when partial mocks come handy: dealing with code you cannot change easily (3rd party interfaces, interim refactoring of legacy code etc.) However, I wouldn't use partial mocks for new, test-driven & well-designed code. 
	}
	
}
