package pl.klolo.workshops.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.klolo.workshops.domain.*;
import pl.klolo.workshops.domain.Currency;
import pl.klolo.workshops.mock.HoldingMockGenerator;
import pl.klolo.workshops.mock.UserMockGenerator;

class WorkShop {

    /**
     * Lista holdingów wczytana z mocka.
     */
    private final List<Holding> holdings;

    // Predykat określający czy użytkownik jest kobietą
    private final Predicate<User> isWoman = null;

    WorkShop() {
        final HoldingMockGenerator holdingMockGenerator = new HoldingMockGenerator();
        holdings = holdingMockGenerator.generate();
    }

    /**
     * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma.
     */
    long getHoldingsWhereAreCompanies() {
        return holdings.stream()
                .filter(h -> !h.getCompanies().isEmpty())
                .count();
    }

    /**
     * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma. Napisz to za pomocą strumieni.
     */
    long getHoldingsWhereAreCompaniesAsStream() {
        return holdings.stream()
                .filter(h -> h.getCompanies().size() > 1)
                .count();
    }

    /**
     * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy.
     */
    List<String> getHoldingNames() {
        return holdings.stream()
                .map(h -> h.getName().toLowerCase())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy. Napisz to za pomocą strumieni.
     */
    List<String> getHoldingNamesAsStream() {
        return holdings.stream()
                .map(h -> h.getName().toLowerCase())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico)
     */
    String getHoldingNamesAsString() {
        return holdings.stream()
                .map(Holding::getName)
                .sorted()
                .collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico). Napisz to za pomocą strumieni.
     */
    String getHoldingNamesAsStringAsStream() {
        return holdings.stream()
                .map(Holding::getName)
                .sorted()
                .collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * Zwraca liczbę firm we wszystkich holdingach.
     */
    long getCompaniesAmount() {
        return holdings.stream()
                .mapToInt(h -> h.getCompanies().size())
                .sum();
    }

    /**
     * Zwraca liczbę firm we wszystkich holdingach. Napisz to za pomocą strumieni.
     */
    long getCompaniesAmountAsStream() {
        return holdings.stream()
                .mapToInt(h -> h.getCompanies().size())
                .sum();
    }

    /**
     * Zwraca liczbę wszystkich pracowników we wszystkich firmach.
     */
    long getAllUserAmount() {
        return getUsersStream()
                .count();

    }

    /**
     * Zwraca liczbę wszystkich pracowników we wszystkich firmach. Napisz to za pomocą strumieni.
     */
    long getAllUserAmountAsStream() {
        return getUsersStream()
                .count();
    }

    /**
     * Zwraca listę wszystkich nazw firm w formie listy.
     */
    List<String> getAllCompaniesNames() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę wszystkich nazw firm w formie listy. Tworzenie strumienia firm umieść w osobnej metodzie którą później będziesz wykorzystywać. Napisz to za
     * pomocą strumieni.
     */
    List<String> getAllCompaniesNamesAsStream() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    Stream<Company> getCompaniesStream() {
        return holdings.stream()
                .flatMap(h -> h.getCompanies().stream());
    }

    Stream<User> getUsersStream() {
        return getCompaniesStream()
                .flatMap(c -> c.getUsers().stream());
    }

    /**
     * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList.
     */
    LinkedList<String> getAllCompaniesNamesAsLinkedList() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList. Obiektów nie przepisujemy po zakończeniu działania strumienia. Napisz to za
     * pomocą strumieni.
     */
    LinkedList<String> getAllCompaniesNamesAsLinkedListAsStream() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+"
     */
    String getAllCompaniesNamesAsString() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.joining("+"));
    }

    /**
     * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+" Napisz to za pomocą strumieni.
     */
    String getAllCompaniesNamesAsStringAsStream() {
        return getCompaniesStream()
                .map(Company::getName)
                .collect(Collectors.joining("+"));
    }

    /**
     * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+". Używamy collect i StringBuilder. Napisz to za pomocą
     * strumieni.
     * <p>
     * UWAGA: Zadanie z gwiazdką. Nie używamy zmiennych.
     */
    String getAllCompaniesNamesAsStringUsingStringBuilder() {
        return getCompaniesStream()
                .map((Company::getName))
                .collect(Collector.of(StringBuilder::new,
                        (stringBuilder, s) -> stringBuilder.append(s).append("+"),
                        StringBuilder::append,
                        stringBuilder -> stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length())))
                .toString();


    }

    /**
     * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach.
     */
    long getAllUserAccountsAmount() {
        return getUsersStream()
                .mapToInt(u -> u.getAccounts().size())
                .sum();
    }

    /**
     * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach. Napisz to za pomocą strumieni.
     */
    long getAllUserAccountsAmountAsStream() {
        return getUsersStream()
                .mapToInt(u -> u.getAccounts().size())
                .sum();
    }

    /**
     * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane.
     */
    String getAllCurrencies() {
        return getAllCurrenciesStream()
                .collect(Collectors.joining(", "));
    }

    /**
     * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane. Napisz to za pomocą strumieni.
     */
    String getAllCurrenciesAsStream() {
        return getAllCurrenciesStream()
                .collect(Collectors.joining(", "));
    }

    /**
     * Metoda zwraca analogiczne dane jak getAllCurrencies, jednak na utworzonym zbiorze nie uruchamiaj metody stream, tylko skorzystaj z  Stream.generate.
     * Wspólny kod wynieś do osobnej metody.
     *
     * @see #getAllCurrencies()
     */
    String getAllCurrenciesUsingGenerate() {
        List<String> currencies = getAllCurrenciesStream().collect(Collectors.toList());

        return Stream.generate(currencies.iterator()::next)
                .collect(Collectors.joining(", "));
    }

    public Stream<String> getAllCurrenciesStream() {
        return getUsersStream()
                .flatMap(u -> u.getAccounts().stream().map(a -> a.getCurrency().toString()))
                .distinct()
                .sorted();

    }

    /**
     * Zwraca liczbę kobiet we wszystkich firmach.
     */
    long getWomanAmount() {
        return getUsersStream()
                .filter(u -> u.getSex().equals(Sex.WOMAN))
                .count();
    }

    /**
     * Zwraca liczbę kobiet we wszystkich firmach. Powtarzający się fragment kodu tworzący strumień uzytkowników umieść w osobnej metodzie. Predicate określający
     * czy mamy doczynienia z kobietą inech będzie polem statycznym w klasie. Napisz to za pomocą strumieni.
     */
    long getWomanAmountAsStream() {
        return getUsersStream()
                .filter(u -> u.getSex().equals(Sex.WOMAN))
                .count();
    }

    public <T> Stream<T> makeStreamOf(List<T> listOf) {
        return listOf.stream();
    }


    /**
     * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Ustaw precyzje na 3 miejsca po przecinku.
     */
    BigDecimal getAccountAmountInPLN(final Account account) {
        return (account.getAmount().multiply(new BigDecimal(account.getCurrency().rate))).setScale(3, RoundingMode.HALF_UP);
    }


    /**
     * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Napisz to za pomocą strumieni.
     */
    BigDecimal getAccountAmountInPLNAsStream(final Account account) {
        return Stream.of(account)
                .map(a -> (a.getAmount().multiply(new BigDecimal(a.getCurrency().rate))).setScale(3, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją.
     */
    BigDecimal getTotalCashInPLN(final List<Account> accounts) {
        return accounts.stream()
                .map(a -> (a.getAmount().multiply(new BigDecimal(a.getCurrency().rate))).setScale(3, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją. Napisz to za pomocą strumieni.
     */
    BigDecimal getTotalCashInPLNAsStream(final List<Account> accounts) {
        return accounts.stream()
                .map(a -> (a.getAmount().multiply(new BigDecimal(a.getCurrency().rate))).setScale(3, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek.
     */
    Set<String> getUsersForPredicate(final Predicate<User> userPredicate) {
        return holdings.stream()
                .flatMap(h -> h.getCompanies().stream()
                        .flatMap(c -> c.getUsers().stream())
                        .filter(userPredicate))
                .map(User::getFirstName)
                .collect(Collectors.toSet());
    }

    /**
     * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek. Napisz to za pomocą strumieni.
     */
    Set<String> getUsersForPredicateAsStream(final Predicate<User> userPredicate) {
        return holdings.stream()
                .flatMap(h -> h.getCompanies().stream()
                        .flatMap(c -> c.getUsers().stream())
                        .filter(userPredicate))
                .map(User::getFirstName)
                .collect(Collectors.toSet());
    }

    /**
     * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy.
     */
    List<String> getOldWoman(final int age) {
        return getUsersStream()
                .filter(u -> u.getAge() > age)
                .peek(u -> System.out.println(u.getFirstName()))
                .filter(u -> !u.getSex().equals(Sex.MAN))
                .map(User::getFirstName)
                .collect(Collectors.toList());
    }

    /**
     * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy. Napisz
     * to za pomocą strumieni.
     */
    List<String> getOldWomanAsStream(final int age) {
        return getUsersStream()
                .filter(u -> u.getAge() > age)
                .peek(u -> System.out.println(u.getFirstName()))
                .filter(u -> !u.getSex().equals(Sex.MAN))
                .map(User::getFirstName)
                .collect(Collectors.toList());
    }

    /**
     * Dla każdej firmy uruchamia przekazaną metodę.
     */
    void executeForEachCompany(final Consumer<Company> consumer) {
        holdings.stream()
                .flatMap(h -> h.getCompanies().stream())
                .forEach(consumer);
    }

    /**
     * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach.
     */
    Optional<User> getRichestWoman() {
        return getUsersStream()
                .filter(u -> u.getSex().equals(Sex.WOMAN))
                .max(Comparator.comparing(user -> getTotalCashInPLN(user.getAccounts())));
    }

    /**
     * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach. Napisz to za pomocą strumieni.
     */
    Optional<User> getRichestWomanAsStream() {
        return getUsersStream()
                .filter(u -> u.getSex().equals(Sex.WOMAN))
                .max(Comparator.comparing(user -> getTotalCashInPLN(user.getAccounts())));
    }

    /**
     * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia.
     */
    Set<String> getFirstNCompany(final int n) {
        return holdings.stream()
                .flatMap(h -> h.getCompanies().stream().map(Company::getName))
                .limit(n)
                .collect(Collectors.toSet());
    }

    /**
     * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia. Napisz to za pomocą strumieni.
     */
    Set<String> getFirstNCompanyAsStream(final int n) {
        return getCompaniesStream()
                .map(Company::getName)
                .limit(n)
                .collect(Collectors.toSet());
    }

    /**
     * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
     * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return.
     */
    AccountType getMostPopularAccountType() {
        return getMostPopularByType();


    }

    public AccountType getMostPopularByType() {
        try {
            return getAccountStream()
                    .map(Account::getType)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .get();
        } catch (NullPointerException | NoSuchElementException e) {
            throw new IllegalStateException();
        }

    }

    /**
     * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
     * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return. Napisz to za pomocą strumieni.
     */
    AccountType getMostPopularAccountTypeAsStream() {
        return getMostPopularByType();
    }

    /**
     * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException.
     */
    User getUser(final Predicate<User> predicate) {
        return getUserChecked(predicate);
    }

    public User getUserChecked(Predicate<User> predicate) {
        try {
            return getUsersStream()
                    .filter(predicate)
                    .findFirst()
                    .get();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException. Napisz to
     * za pomocą strumieni.
     */
    User getUserAsStream(final Predicate<User> predicate) {
        return getUserChecked(predicate);
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników.
     */
    Map<String, List<User>> getUserPerCompany() {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName, Company::getUsers));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników. Napisz to za pomocą strumieni.
     */
    Map<String, List<User>> getUserPerCompanyAsStream() {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName, Company::getUsers));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
     * Możesz skorzystać z metody entrySet.
     */
    Map<String, List<String>> getUserPerCompanyAsString() {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName,
                        (value) -> value.getUsers().stream().
                                map(u -> String.format("%s %s", u.getFirstName(), u.getLastName()))
                                .collect(Collectors.toList())));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
     * Możesz skorzystać z metody entrySet. Napisz to za pomocą strumieni.
     */
    Map<String, List<String>> getUserPerCompanyAsStringAsStream() {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName, v -> v.getUsers().stream().map(u -> u.getFirstName() + " " + u.getLastName()).collect(Collectors.toList())));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej
     * funkcji.
     */
    <T> Map<String, List<T>> getUserPerCompany(final Function<User, T> converter) {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName,
                        v -> v.getUsers().stream()
                                .map(converter)
                                .collect(Collectors.toList())));
    }

    /**
     * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej funkcji.
     * Napisz to za pomocą strumieni.
     */
    <T> Map<String, List<T>> getUserPerCompanyAsStream(final Function<User, T> converter) {
        return getCompaniesStream()
                .collect(Collectors.toMap(Company::getName,
                        v -> v.getUsers().stream()
                                .map(converter)
                                .collect(Collectors.toList())));
    }

    /**
     * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
     * jest natomiast zbiór nazwisk tych osób. Napisz to za pomocą strumieni.
     */
    Map<Boolean, Set<String>> getUserBySexAsStream() {
        return getUsersStream()
                .filter(user -> !user.getSex().equals(Sex.OTHER))
                .collect(Collectors.partitioningBy(user -> user.getSex().equals(Sex.MAN), Collectors.mapping(User::getLastName, Collectors.toSet())));
    }

    /**
     * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
     * jest natomiast zbiór nazwisk tych osób.
     */
    Map<Boolean, Set<String>> getUserBySex() {
        return getUsersStream()
                .filter(user -> !user.getSex().equals(Sex.OTHER))
                .collect(Collectors.partitioningBy(u -> u.getSex().equals(Sex.MAN), Collectors.mapping(User::getLastName, Collectors.toSet())));
    }

    /**
     * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek.
     */
    Map<String, Account> createAccountsMap() {
        return getAccountStream()
                .collect(Collectors.toMap(Account::getNumber, a -> a));
    }

    /**
     * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek. Napisz to za pomocą strumieni.
     */
    Map<String, Account> createAccountsMapAsStream() {
        return getAccountStream()
                .collect(Collectors.toMap(Account::getNumber, e -> e));
    }

    /**
     * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń.
     */
    String getUserNames() {
        return getUsersStream()
                .map(User::getFirstName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(" "));
    }

    /**
     * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń. Napisz to za pomocą strumieni.
     */
    String getUserNamesAsStream() {
        return getUsersStream()
                .map(User::getFirstName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(" "));
    }

    /**
     * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10.
     */
    Set<User> getUsers() {
        return getUsersStream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    /**
     * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10. Napisz to za pomocą strumieni.
     */
    Set<User> getUsersAsStream() {
        return getUsersStream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    /**
     * Zwraca użytkownika, który spełnia podany warunek.
     */
    Optional<User> findUser(final Predicate<User> userPredicate) {
        return getUsersStream()
                .filter(userPredicate)
                .findFirst();
    }

    /**
     * Zwraca użytkownika, który spełnia podany warunek. Napisz to za pomocą strumieni.
     */
    Optional<User> findUserAsStream(final Predicate<User> userPredicate) {
        return getUsersStream()
                .filter(userPredicate)
                .findFirst();
    }

    /**
     * Dla podanego użytkownika zwraca informacje o tym ile ma lat w formie: IMIE NAZWISKO ma lat X. Jeżeli użytkownik nie istnieje to zwraca text: Brak
     * użytkownika.
     * <p>
     * Uwaga: W prawdziwym kodzie nie przekazuj Optionali jako parametrów. Napisz to za pomocą strumieni.
     */
    String getAdultantStatusAsStream(final Optional<User> user) {
        try {
            return getUsersStream()
                    .filter(u -> u.equals(user.get()))
                    .map(u -> String.format("%s %s ma lat %d", u.getFirstName(), u.getLastName(), u.getAge()))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return "Brak użytkownika";
        }

    }

    /**
     * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
     * Pasibrzuch, Adam Wojcik
     */
    void showAllUser() {
        getUsersStream()
                .sorted(Comparator.comparing(User::getFirstName).reversed())
                .forEachOrdered(u -> System.out.println(u.getFirstName() + " " + u.getLastName()));
    }

    /**
     * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
     * Pasibrzuch, Adam Wojcik. Napisz to za pomocą strumieni.
     */
    void showAllUserAsStream() {
        getUsersStream()
                .sorted(Comparator.comparing(User::getFirstName).reversed())
                .forEachOrdered(u -> System.out.println(u.getFirstName() + " " + u.getLastName()));
    }

    /**
     * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki.
     */
    Map<AccountType, BigDecimal> getMoneyOnAccounts() {
        return getAccountStream()
                .collect(Collectors.groupingBy(Account::getType, Collectors.reducing(BigDecimal.ZERO, this::getAccountAmountInPLN, BigDecimal::add)));
    }

    /**
     * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki. Napisz to za pomocą
     * strumieni. Ustaw precyzje na 0
     */
    Map<AccountType, BigDecimal> getMoneyOnAccountsAsStream() {
        return getAccountStream()
                .collect(Collectors.groupingBy(Account::getType, Collectors.reducing(BigDecimal.ZERO.setScale(0, RoundingMode.HALF_UP), this::getAccountAmountInPLN, BigDecimal::add)));
    }

    /**
     * Zwraca sumę kwadratów wieków wszystkich użytkowników.
     */
    int getAgeSquaresSum() {
        return getUsersStream()
                .map(User::getAge)
                .map(age -> Math.pow(age, 2))
                .reduce(0.0, Double::sum)
                .intValue();
    }

    /**
     * Zwraca sumę kwadratów wieków wszystkich użytkowników. Napisz to za pomocą strumieni.
     */
    int getAgeSquaresSumAsStream() {
        return getUsersStream()
                .map(User::getAge)
                .map(age -> Math.pow(age, 2))
                .reduce(0.0, Double::sum)
                .intValue();
    }

    /**
     * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
     * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody).
     */
    List<User> getRandomUsers(final int n) {
        if (n <= getUsersStream().count()) {
            return getUsersStream()
                    .distinct()
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    }))
                    .limit(n)
                    .collect(Collectors.toList());
        }
        throw new IndexOutOfBoundsException("Podana liczba wiksza niz liczba uzytkownikow");
    }

    /**
     * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
     * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody). Napisz to za pomocą strumieni.
     */
    List<User> getRandomUsersAsStream(final int n) {
        final UserMockGenerator userMockGenerator = new UserMockGenerator();
        return Optional.of(userMockGenerator.generate().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .distinct()
                .limit(n)
                .collect(Collectors.toList()))
                .orElseThrow(ArrayIndexOutOfBoundsException::new);
    }

    /**
     * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
     * na rachunku danego typu przeliczona na złotkówki.
     */
    Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMap() {
        return getAccountStream()
                .map(Account::getType)
                .collect(Collectors.toMap(
                        accountTypeKey -> accountTypeKey,
                        this::mapUsersByAccountType,
                        (a1, a2) -> a1));
    }

    private Map<User, BigDecimal> mapUsersByAccountType(AccountType accountType) {
        return getUserStream()
                .filter(user -> user.getSex().equals(Sex.MAN) &&
                        user.getAccounts().stream()
                                .anyMatch(account -> account.getType().equals(accountType)))
                .collect(Collectors.toMap(
                        theUserKey -> theUserKey,
                        theUserValue -> sumUserSpecificAccountTypeMoneyInPLN(theUserValue, accountType),
                        (a1, a2) -> a1));
    }

    private BigDecimal sumUserSpecificAccountTypeMoneyInPLN(User user, AccountType type) {
        return getUserStream()
                .filter(u -> u.equals(user))
                .flatMap(u -> u.getAccounts().stream())
                .filter(account -> account.getType().equals(type))
                .map(this::getAccountAmountInPLN)
                .map(b -> b.setScale(2,RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }


    /**
     * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
     * na rachunku danego typu przeliczona na złotkówki.  Napisz to za pomocą strumieni.
     */
    Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMapAsStream() {
        return getAccountStream()
                .map(Account::getType)
                .collect(Collectors.toMap(
                        accountTypeKey -> accountTypeKey,
                        this::mapUsersByAccountType,
                        (a1, a2) -> a1));
    }

    /**
     * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
     * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach.
     */

    Map<Permit, List<User>> getUsersByTheyPermitsSorted() {
        return getUserStream().flatMap(user -> user.getPermits().stream())
                .collect(Collectors.toMap(
                        Function.identity(),
                        permit -> getUserStream()
                                .filter(user -> user.getPermits().contains(permit))
                                .sorted((o1, o2) -> getTotalCashInPLN(o2.getAccounts()).subtract(getTotalCashInPLN(o1.getAccounts())).intValue())
                                .collect(Collectors.toList()),
                        (a1, a2) -> a1
                ));
    }

    /**
     * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
     * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach. Napisz to za pomoca strumieni.
     */

    Map<Permit, List<User>> getUsersByTheyPermitsSortedAsStream() {
        return getUserStream().flatMap(user -> user.getPermits().stream())
                .collect(Collectors.toMap(
                        Function.identity(),
                        permit -> getUserStream()
                                .filter(user -> user.getPermits().contains(permit))
                                .sorted((o1, o2) -> getTotalCashInPLN(o2.getAccounts()).subtract(getTotalCashInPLN(o1.getAccounts())).intValue())
                                .collect(Collectors.toList()),
                        (a1, a2) -> a1
                ));
    }

    /**
     * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
     * List<Users>
     */
    Map<Boolean, List<User>> divideUsersByPredicate(final Predicate<User> predicate) {
        return getUsersStream()
                .collect(Collectors.partitioningBy(predicate));
    }

    /**
     * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
     * List<Users>. Wykonaj zadanie za pomoca strumieni.
     */
    Map<Boolean, List<User>> divideUsersByPredicateAsStream(final Predicate<User> predicate) {
        return getUsersStream()
                .collect(Collectors.partitioningBy(predicate));
    }

    /**
     * Zwraca strumień wszystkich firm.
     */
    private Stream<Company> getCompanyStream() {
        return holdings.stream()
                .flatMap(h -> h.getCompanies().stream());
    }

    /**
     * Zwraca zbiór walut w jakich są rachunki.
     */
    private Set<Currency> getCurenciesSet() {
        return new HashSet<>(Arrays.asList(Currency.values()));
    }

    /**
     * Tworzy strumień rachunków.
     */
    private Stream<Account> getAccountStream() {
        return getUsersStream()
                .flatMap(u -> u.getAccounts().stream());
    }

    /**
     * Tworzy strumień użytkowników.
     */
    private Stream<User> getUserStream() {
        return getCompaniesStream()
                .flatMap(c -> c.getUsers().stream());
    }

}
