package org.hansib.sundries;

import java.util.function.BiFunction
import java.util.function.Function

import spock.lang.Specification
import spock.lang.Unroll

public class CollectUtilsSpec extends Specification {

	@Unroll def"get empty combinations for size #size"() {
		given:
		def x = CollectUtils.combinations([1, 2, 3] as SortedSet, size)

		expect:
		x.toList() == []

		where:
		size << [-1, 0]
	}

	def"get sole combination"() {

		expect:
		CollectUtils.combinations([1, 2, 3] as SortedSet, 3).toList() == sortedTreeSets([[1, 2, 3]])
	}

	def"get combinations 1 from 3"() {

		expect:
		CollectUtils.combinations([1, 2, 3] as SortedSet, 1).toList() == sortedTreeSets([[1], [2], [3]])
	}

	def"get more combinations"() {

		expect:
		CollectUtils.combinations([1, 2, 3, 4, 5] as SortedSet, 3).toList() == sortedTreeSets([
			[1, 2, 3],
			[1, 2, 4],
			[1, 3, 4],
			[2, 3, 4],
			[1, 2, 5],
			[1, 3, 5],
			[2, 3, 5],
			[1, 4, 5],
			[2, 4, 5],
			[3, 4, 5]
		])
	}

	def"get combinations for custom comparator"() {

		given:
		def c1 = "c1"
		def c2 = "c2"
		def c3 = "c3"

		expect:
		CollectUtils.combinations([c1, c2, c3] as SortedSet, 2).toList() == sortedTreeSets([
			[c1, c2],
			[c1, c3],
			[c2, c3]
		])
	}

	def sortedTreeSets(final collOfColls) {
		collOfColls.collect {
			new TreeSet(it)
		}
	}

	def "pair combinations"() {
		expect:
		CollectUtils.pairCombinations(0).toList() == []
		CollectUtils.pairCombinations(1).toList() == []
		CollectUtils.pairCombinations(2).toList() == [[0, 1]]
		CollectUtils.pairCombinations(3).toList() == [[0, 1], [0, 2], [1, 2]]
		CollectUtils.pairCombinations(4).toList() == [
			[0, 1],
			[0, 2],
			[0, 3],
			[1, 2],
			[1, 3],
			[2, 3]
		]
	}

	def "pair combinations from list"() {
		given:
		def c1 = "c1"
		def c2 = "c2"
		def c3 = "c3"

		expect:
		CollectUtils.pairCombinations(List.of(c1, c2, c3)).toList() == [[c1, c2], [c1, c3], [c2, c3]]
	}

	def "intersection for no sets"() {
		expect:
		CollectUtils.intersection() == Collections.emptySet()
	}

	def "intersection for single list is set"() {
		expect:
		CollectUtils.intersection([1, 1, 2]) == [1, 2] as Set
	}

	def "intersection for three collections"() {
		given:
		def a = 1 .. 4 as Set
		def b = [1, 2, 2]
		def c = [2, 3] as Set

		expect:
		CollectUtils.intersection(a, b, c) == [2] as Set
	}

	def "union for no sets"() {
		expect:
		CollectUtils.union() == Collections.emptySet()
	}

	def "union for single list is set"() {
		expect:
		CollectUtils.union([1, 1, 2]) == [1, 2] as Set
	}

	def "union for three collections"() {
		given:
		def a = 1 .. 4 as Set
		def b = [2, 1, 2]
		def c = [22, 3] as Set

		expect:
		CollectUtils.union(a, b, c) == [1, 2, 3, 4, 22] as Set
	}

	def "map sorted map to list"() {
		given:
		def addit = { Integer a, Integer b ->
			a + b
		} as BiFunction
		expect:
		CollectUtils.mapMapToList([7: 2, 1: 2, 3 : 4] as TreeMap, addit) == [3, 7, 9]
	}

	def "invert simple numbers map"() {
		given:
		def newSet = { k ->
			new HashSet()
		} as Function
		expect:
		CollectUtils.invertMap([7: 2, 1: 2, 3 : 4], newSet, new HashMap()) == [2:[1, 7] as Set, 4:[3] as Set]
	}

	def "can use sorted set comparator"() {

		given:
		def ssc = CollectUtils.sortedSetComparator()

		expect:
		ssc.compare(soSe([2, 3]), soSe([2, 3])) == 0

		ssc.compare(soSe([2, 3]), soSe([1, 3])) == 1

		ssc.compare(soSe([2, 3]), soSe([3])) == -1
		ssc.compare(soSe([3]), soSe([2, 3])) == 1

		ssc.compare(soSe([2, 3]), soSe([2])) == 1
	}

	def soSe(ele) {
		return ele as SortedSet
	}
}
