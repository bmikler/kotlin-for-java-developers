package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filterTo(HashSet()) { driver -> trips.none { it.driver == driver } }

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = allPassengers.filterTo(HashSet()) { passenger ->
    trips.count { trip -> passenger in trip.passengers } >= minTrips
}



fun TaxiPark.test() : Boolean = 
/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    trips.filter { it.driver == driver }
        .flatMap(Trip::passengers).groupingBy { passenger -> passenger }.eachCount()
        .filterValues { it > 1 }
        .keys

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> = trips.flatMap { trip ->
    trip.passengers.map { passenger -> passenger to trip.discount } }
    .groupByTo(HashMap(), { it.first }, { it.second })
    .filterValues { discounts ->
        discounts.count { discount -> discount != null } > discounts.size / 2
    }.keys


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    trips.map { trip -> IntRange(trip.duration / 10 * 10, trip.duration / 10 * 10 + 9) }
        .groupingBy { it }.eachCount()
        .maxByOrNull { (_, frequency) -> frequency}?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
//fun TaxiPark.checkParetoPrinciple(): Boolean =
//    allDrivers.map { driver -> driver to trips.filter { trip -> trip.driver == driver }.sumOf { it.cost } }
//        .sortedBy { it.second }.reversed()
//        .take((allDrivers.size * 0.2).toInt())
//        .sumOf { it.second } > (trips.sumOf { it.cost } * 0.79999999)
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumByDouble(Trip::cost)
    val sortedDriversIncome: List<Double> = trips
        .groupBy { it.driver }
        .map { (_, tripsByDriver) -> tripsByDriver.sumByDouble(Trip::cost) }
        .sortedDescending()

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
        .take(numberOfTopDrivers)
        .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}