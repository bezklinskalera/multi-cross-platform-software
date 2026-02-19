import java.awt.*
import javax.swing.*
import javax.swing.table.DefaultTableModel
import kotlin.math.pow

fun main() {
    SwingUtilities.invokeLater { CalculatorWindow() }
}

class CalculatorWindow : JFrame("Калькулятор електричних навантажень") {

    private val inputs = mutableMapOf<String, JTextField>()
    private val tableModel = DefaultTableModel()
    private val resultTable = JTable(tableModel)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(1200, 600)
        layout = BorderLayout()

        val inputPanel = JPanel(GridLayout(3, 2, 10, 10))
        val labels = listOf(
            "Номінальна потужність ЕП (кВт)" to "21",
            "Коефіцієнт використання" to "0.22",
            "Коефіцієнт реактивної потужності" to "1.56"
        )

        labels.forEach { (name, default) ->
            val label = JLabel(name)
            val field = JTextField(default)
            inputs[name] = field
            inputPanel.add(label)
            inputPanel.add(field)
        }

        val button = JButton("Обчислити")
        button.addActionListener { calculate() }

        tableModel.setColumnIdentifiers(
            arrayOf("ЕП", "Кількість", "Рн", "n*Рн", "КВ", "tgφ", "n*Рн*Кв*tgφ")
        )

        add(inputPanel, BorderLayout.NORTH)
        add(JScrollPane(resultTable), BorderLayout.CENTER)
        add(button, BorderLayout.SOUTH)

        isVisible = true
    }

    private fun calculate() {
        tableModel.rowCount = 0

        // Дані прикладу (можна розширювати для всіх ЕП)
        val epList = listOf(
            Triple("Шліфувальний верстат", 4, 20),
            Triple("Свердлильний верстат", 2, 14),
            Triple("Циркулярна пила", 1, 36)
        )

        val kv = inputs["Коефіцієнт використання"]!!.text.toDouble()
        val tg = inputs["Коефіцієнт реактивної потужності"]!!.text.toDouble()

        epList.forEach { (name, n, rNom) ->
            val nPn = n * rNom
            val nPnKvTg = nPn * kv * tg
            tableModel.addRow(arrayOf(name, n, rNom, nPn, kv, tg, nPnKvTg))
        }
    }
}
