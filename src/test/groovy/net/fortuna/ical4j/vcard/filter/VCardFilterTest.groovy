package net.fortuna.ical4j.vcard.filter

import net.fortuna.ical4j.filter.FilterExpression
import net.fortuna.ical4j.vcard.ContentBuilder
import spock.lang.Shared
import spock.lang.Specification

class VCardFilterTest extends Specification {

    @Shared
    ContentBuilder builder

    def setupSpec() {
        builder = new ContentBuilder()
    }

    def 'test filter expression equals'() {
        given: 'a filter expression'
        def expression = FilterExpression.equalTo('fn', 'Joe Bloggs')

        and: 'a card'
        def card = builder.vcard {
            fn 'Joe Bloggs'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(expression).test(card)
    }

    def 'test filter expression comparison'() {
        given: 'a filter expression'
        def filter = FilterExpression.lessThanEqual('bday', '19750101')

        and: 'a card'
        def card = builder.vcard {
            fn 'Joe Bloggs'
            bday '19740101'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression comparison function'() {
        given: 'a filter expression that checks for adult age (18)'
        def filter = FilterExpression.lessThanEqual('bday', 'now(-P936W)')

        and: 'a card'
        def card = builder.vcard {
            fn 'Sally Cap'
            bday '19980609'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression in'() {
        given: 'a filter expression'
        def filter = FilterExpression.in('member', ['fred@example.com'])

        and: 'a card'
        def card = builder.vcard {
            fn 'Calendar Experts'
            kind 'group'
            member 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression contains'() {
        given: 'a filter expression'
        def filter = FilterExpression.contains('email', 'example.com')

        and: 'a card'
        def card = builder.vcard {
            fn 'Fred Savage'
            kind 'individual'
            email 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression missing'() {
        given: 'a filter expression'
        def filter = FilterExpression.notExists('tel')

        and: 'a card'
        def card = builder.vcard {
            fn 'Fred Savage'
            kind 'individual'
            email 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression not missing'() {
        given: 'a filter expression'
        def filter = FilterExpression.exists('email')

        and: 'a card'
        def card = builder.vcard {
            fn 'Fred Savage'
            kind 'individual'
            email 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

    def 'test filter expression parsing'() {
        given: 'a card'
        def card = builder.vcard {
            fn 'Fred Savage'
            kind 'individual'
            email 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(FilterExpression.parse(expression)).test(card) == expectedResult

        where: 'filter expression'
        expression                                            | expectedResult
        'kind= "individual" and email contains "example.com"' | true
        'fn contains "Savage"'                                | true
        'email contains "C@example.com"'                      | false
        'photo not exists'                                    | true
        'kind not exists'                                     | false
        'fn exists'                                           | true
    }

    def 'test filter expressions with sets'() {
        given: 'a filter expression using sets'
        def filter = FilterExpression.in('kind', ['individual'] as Set)

        and: 'a card'
        def card = builder.vcard {
            fn 'Fred Savage'
            kind 'individual'
            email 'fred@example.com'
        }

        expect: 'filter matches the event'
        new VCardFilter().predicate(filter).test(card)
    }

}
