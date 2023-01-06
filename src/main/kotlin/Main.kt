enum class MachineMode {
    BUY, FILL, TAKE, REMAINING, EXIT
}

enum class CoffeeOptions {
    ESPRESSO, LATTE, CAPPUCCINO
}

data class Storage(
    var availableWater: UInt = 400U,//askForAvailableWater()
    var availableMilk: UInt = 540U, //askForAvailableMilk()
    var availableBeans: UInt = 120U, //askForAvailableBeans()
    var availableDisposableCups: UInt = 9U,
    var money: UInt = 550U
)

fun main() {

    val storage = Storage()

    val isMachineWorking: Boolean = true
    //Ask user for the machine mode
    while (isMachineWorking){
        val machineMode = askUserForMachineRole()

        when (machineMode) {
            MachineMode.BUY -> executeBuyMode(storage)
            MachineMode.FILL -> executeFillMode(storage)
            MachineMode.TAKE -> executeTakeMode(storage)
            MachineMode.REMAINING -> storage.printContent()
            MachineMode.EXIT -> {
                return
            }
        }

    }
}

fun executeTakeMode(storage: Storage) {
    println("I gave you \$${storage.money}")
    storage.money = 0U
}

fun executeFillMode(storage: Storage) {
    storage.availableWater += askForAvailableWater()
    storage.availableMilk += askForAvailableMilk()
    storage.availableBeans += askForAvailableBeans()
    storage.availableDisposableCups += askForAvailableDisposableCups()
}

fun executeBuyMode(storage: Storage) {
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")

    val selectedCoffeeOption: CoffeeOptions = when (readln()) {
        "1" -> CoffeeOptions.ESPRESSO
        "2" -> CoffeeOptions.LATTE
        "3" -> CoffeeOptions.CAPPUCCINO
        else -> return
    }

    if (!areThereEnoughMaterialsFor(selectedCoffeeOption, storage)) return

    when (selectedCoffeeOption) {
        CoffeeOptions.ESPRESSO -> {

            storage.availableDisposableCups -= 1U
            storage.availableWater -= 250U
            storage.availableBeans -= 16U
            storage.money += 4U


        }

        CoffeeOptions.LATTE -> {
            storage.availableDisposableCups -= 1U
            storage.availableWater -= 350U
            storage.availableMilk -= 75U
            storage.availableBeans -= 20U
            storage.money += 7U
        }

        CoffeeOptions.CAPPUCCINO -> {
            storage.availableDisposableCups -= 1U
            storage.availableWater -= 200U
            storage.availableMilk -= 100U
            storage.availableBeans -= 12U
            storage.money += 6U
        }
    }

    println("I have enough resources, making you a coffee!")

}

fun areThereEnoughMaterialsFor(selectedCoffeeOption: CoffeeOptions, storage: Storage): Boolean {
    when(selectedCoffeeOption){
        CoffeeOptions.ESPRESSO -> {
            when{
                storage.availableWater < 250U -> {
                    println("Sorry, not enough water!")
                    return false
                }
                storage.availableBeans < 16U -> {
                    println("Sorry, not enough coffee beans!")
                    return false
                }
                storage.availableDisposableCups < 1U -> {
                    println("Sorry, not enough disposable cups!")
                    return false
                }
                else -> return true
            }

        }
        CoffeeOptions.LATTE -> {
            when{
                storage.availableWater < 350U -> {
                    println("Sorry, not enough water!")
                    return false
                }
                storage.availableMilk < 75U -> {
                    println("Sorry, not enough milk!")
                    return false
                }
                storage.availableBeans < 16U -> {
                    println("Sorry, not enough coffee beans!")
                    return false
                }
                storage.availableDisposableCups < 1U -> {
                    println("Sorry, not enough disposable cups!")
                    return false
                }
                else -> return true
            }

        }
        CoffeeOptions.CAPPUCCINO -> {
            when{
                storage.availableWater < 200U -> {
                    println("Sorry, not enough water!")
                    return false
                }
                storage.availableMilk < 100U -> {
                    println("Sorry, not enough milk!")
                    return false
                }
                storage.availableBeans < 12U -> {
                    println("Sorry, not enough coffee beans!")
                    return false
                }
                storage.availableDisposableCups < 1U -> {
                    println("Sorry, not enough disposable cups!")
                    return false
                }
                else -> return true
            }
        }
    }
}

fun Storage.printContent() {
    println(
        """
        The coffee machine has:
        ${this.availableWater} ml of water
        ${this.availableMilk} ml of milk
        ${this.availableBeans} g of coffee beans
        ${this.availableDisposableCups} disposable cups
        $${this.money} of money
    """.trimIndent()
    )
}

fun askUserForMachineRole(): MachineMode {
    println("Write action (buy, fill, take, remaining, exit): ")
    readln().let {
        when (it) {
            "buy" -> return MachineMode.BUY
            "fill" -> return MachineMode.FILL
            "take" -> return MachineMode.TAKE
            "remaining" -> return MachineMode.REMAINING
            "exit" -> return MachineMode.EXIT
            else -> return MachineMode.BUY
        }
    }
}

fun askAndPrintHowManyCupsCanTheMachineMake(
    numberOfRequestedCups: Int,
    maxAvailableCups: Int
) {
    when {
        maxAvailableCups == 0 && numberOfRequestedCups > 0 -> println("No, I can make only $maxAvailableCups cups of coffee")
        maxAvailableCups == 0 && numberOfRequestedCups < 0 -> println("Yes, I can make that amount of coffee")
        numberOfRequestedCups == maxAvailableCups -> println("Yes, I can make that amount of coffee")
        numberOfRequestedCups > maxAvailableCups -> println("No, I can make only $maxAvailableCups cups of coffee")
        else -> println("Yes, I can make that amount of coffee (and even ${maxAvailableCups - numberOfRequestedCups} more than that)")
    }
}

fun askForAvailableWater(): UInt {
    println("Write how many ml of water you want to add:")
    return try {
        readln().toUInt()
    } catch (ex: java.lang.Exception) {
        0U
    }
}

fun askForAvailableMilk(): UInt {
    println("Write how many ml of milk you want to add:")
    return try {
        readln().toUInt()
    } catch (ex: java.lang.Exception) {
        0U
    }
}

fun askForAvailableBeans(): UInt {
    println("Write how many grams of coffee beans you want to add:")
    return try {
        readln().toUInt()
    } catch (ex: java.lang.Exception) {
        0U
    }
}

fun askForAvailableDisposableCups(): UInt {
    println("Write how many disposable cups you want to add:")
    return try {
        readln().toUInt()
    } catch (ex: java.lang.Exception) {
        0U
    }
}

fun askForRequestedCoffee(): Int {
    println("Write how many cups of coffee you will need:")
    return try {
        readln().toInt()
    } catch (ex: java.lang.Exception) {
        0
    }
}

fun printResult(numberOfCups: Int, water: Int, milk: Int, beans: Int) {
    println(
        """
            For $numberOfCups cups of coffee you will need:
            $water ml of water
            $milk ml of milk
            $beans g of coffee beans
        """.trimIndent()
    )
}

fun askForNumberOfCups(): Int {
    return try {
        readln().toInt()
    } catch (ex: Exception) {
        0
    }
}

fun printGenericMessage() {
    println(startingMessage)
}

private var startingMessage = """
Starting to make a coffee
Grinding coffee beans
Boiling water
Mixing boiled water with crushed coffee beans
Pouring coffee into the cup
Pouring some milk into the cup
Coffee is ready!
""".trimIndent()
