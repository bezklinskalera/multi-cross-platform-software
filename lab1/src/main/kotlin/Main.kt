import java.awt.*
import javax.swing.*
import javax.swing.border.TitledBorder
import kotlin.math.round

fun main() {
    SwingUtilities.invokeLater {
        FuelLabApp()
    }
}

class FuelLabApp {

    private val frame = JFrame("Практична робота №1 – Паливо")


    private val hField = JTextField("4.2")
    private val cField = JTextField("62.1")
    private val sField = JTextField("3.30")
    private val nField = JTextField("1.2")
    private val oField = JTextField("6.40")
    private val wField = JTextField("7")
    private val aField = JTextField("15.8")
    private val resultArea1 = JTextArea(8, 40)


    private val c2Field = JTextField("85.50")
    private val h2Field = JTextField("11.20")
    private val o2Field = JTextField("0.80")
    private val s2Field = JTextField("2.5")
    private val w2Field = JTextField("2")
    private val a2Field = JTextField("0.15")
    private val v2Field = JTextField("333.3")
    private val resultArea2 = JTextArea(6, 40)

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(850, 700)
        frame.layout = BorderLayout()

        val mainPanel = JPanel()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        mainPanel.add(createTask1Panel())
        mainPanel.add(Box.createVerticalStrut(20)) // відстань між завданнями
        mainPanel.add(createTask2Panel())

        frame.add(JScrollPane(mainPanel))
        frame.isVisible = true
    }


    private fun createTask1Panel(): JPanel {
        val panel = JPanel()
        panel.layout = GridBagLayout()
        panel.border = TitledBorder("Завдання №1: Склад компонентів палива")

        val gbc = GridBagConstraints()
        gbc.insets = Insets(5,5,5,5)
        gbc.fill = GridBagConstraints.HORIZONTAL

        val labels = listOf("H %","C %","S %","N %","O %","W %","A %")
        val fields = listOf(hField, cField, sField, nField, oField, wField, aField)

        for (i in labels.indices) {
            gbc.gridx = 0; gbc.gridy = i
            panel.add(JLabel(labels[i]), gbc)
            gbc.gridx = 1
            panel.add(fields[i], gbc)
        }


        val button = JButton("Обчислити")
        button.background = Color(34, 139, 34)
        button.foreground = Color.WHITE
        button.font = Font("Arial", Font.BOLD, 14)
        button.preferredSize = Dimension(200, 40)
        button.addActionListener { calculateTask1() }

        gbc.gridx = 0; gbc.gridy = labels.size
        gbc.gridwidth = 2
        panel.add(button, gbc)

        // Результат
        resultArea1.isEditable = false
        resultArea1.font = Font("Monospaced", Font.PLAIN, 12)
        gbc.gridy = labels.size + 1
        panel.add(JScrollPane(resultArea1), gbc)

        return panel
    }

    private fun calculateTask1() {
        val h = hField.text.toDoubleOrNull() ?: 0.0
        val c = cField.text.toDoubleOrNull() ?: 0.0
        val s = sField.text.toDoubleOrNull() ?: 0.0
        val n = nField.text.toDoubleOrNull() ?: 0.0
        val o = oField.text.toDoubleOrNull() ?: 0.0
        val w = wField.text.toDoubleOrNull() ?: 0.0
        val a = aField.text.toDoubleOrNull() ?: 0.0

        val krs = 100 / (100 - w)
        val krg = 100 / (100 - w - a)

        val nRob = (339 * c + 1030 * h - 108.8 * (o - s) - 25 * w) / 1000
        val nSyha = ((nRob + 0.025 * w) * 100) / (100 - w)
        val nGor = ((nRob + 0.025 * w) * 100) / (100 - w - a)

        resultArea1.text = """
            Коефіцієнт переходу:
              Робоча → суха маса: ${round2(krs)}
              Робоча → горюча маса: ${round2(krg)}
            
            Нижча теплота згоряння:
              Робоча маса: ${round2(nRob)} МДж/кг
              Суха маса: ${round2(nSyha)} МДж/кг
              Горюча маса: ${round2(nGor)} МДж/кг
        """.trimIndent()
    }


    private fun createTask2Panel(): JPanel {
        val panel = JPanel()
        panel.layout = GridBagLayout()
        panel.border = TitledBorder("Завдання №2: Склад горючої маси мазуту")

        val gbc = GridBagConstraints()
        gbc.insets = Insets(5,5,5,5)
        gbc.fill = GridBagConstraints.HORIZONTAL

        val labels = listOf("C %","H %","O %","S %","W %","A %","V %")
        val fields = listOf(c2Field, h2Field, o2Field, s2Field, w2Field, a2Field, v2Field)

        for (i in labels.indices) {
            gbc.gridx = 0; gbc.gridy = i
            panel.add(JLabel(labels[i]), gbc)
            gbc.gridx = 1
            panel.add(fields[i], gbc)
        }

        val button2 = JButton("Обчислити")
        button2.background = Color(34, 139, 34)
        button2.foreground = Color.WHITE
        button2.font = Font("Arial", Font.BOLD, 14)
        button2.preferredSize = Dimension(200, 40)
        button2.addActionListener { calculateTask2() }

        gbc.gridx = 0; gbc.gridy = labels.size
        gbc.gridwidth = 2
        panel.add(button2, gbc)

        // Результат
        resultArea2.isEditable = false
        resultArea2.font = Font("Monospaced", Font.PLAIN, 12)
        gbc.gridy = labels.size + 1
        panel.add(JScrollPane(resultArea2), gbc)

        return panel
    }

    private fun calculateTask2() {
        val c = c2Field.text.toDoubleOrNull() ?: 0.0
        val h = h2Field.text.toDoubleOrNull() ?: 0.0
        val o = o2Field.text.toDoubleOrNull() ?: 0.0
        val s = s2Field.text.toDoubleOrNull() ?: 0.0
        val w = w2Field.text.toDoubleOrNull() ?: 0.0
        val a = a2Field.text.toDoubleOrNull() ?: 0.0
        val v = v2Field.text.toDoubleOrNull() ?: 0.0

        val teplotaGor = 40.4
        val teplotaRob = teplotaGor * ((100 - w - a) / 100) - 0.025 * w

        resultArea2.text = """
            Нижча теплота горючої маси: 40.40 МДж/кг
            Теплота згоряння (горюча → робоча): ${round2(teplotaRob)} МДж/кг
        """.trimIndent()
    }

    private fun round2(value: Double): Double = round(value * 100) / 100
}
