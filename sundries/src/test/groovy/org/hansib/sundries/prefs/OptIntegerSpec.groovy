package org.hansib.sundries.prefs

import spock.lang.Specification

public class OptIntegerSpec extends Specification {

	Prefs<TestKey> store = Mock()
	OptInteger<TestKey> p = new OptInteger(TestKey.one, store)

	def 'defaults to empty value'(){

		expect:
		p.get() == Optional.empty()
	}

	def 'can set value'(){

		when:
		p.set(34)

		then:
		1 * store.set(p, 34)
	}

	def 'can get given value'(){

		given:
		store.get(p) >> 78

		expect:
		p.get() == Optional.of(78)
	}
}
