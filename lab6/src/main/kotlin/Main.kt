import java.awt.*
import javax.swing.*
import kotlin.math.*

fun main() {
    SwingUtilities.invokeLater { SolarProfitCalculator().createAndShowGUI() }
}

class SolarProfitCalculator {
    private val frame = JFrame("Лабораторна робота №6: Розрахунок прибутку СЕС")
    private val serDPotField = JTextField("5", 5)
    private val serKvadrVidField = JTextField("1", 5)
    private val serKvadrVidZmenField = JTextField("0.25", 5)
    private val vartistField = JTextField("7", 5)

    private val chastkaEnLabel = JLabel("0")
    private val w1Label = JLabel("0")
    private val prub1Label = JLabel("0")
    private val w2Label = JLabel("0")
    private val sh1Label = JLabel("0")
    private val chastkaEn2Label = JLabel("0")
    private val w3Label = JLabel("0")
    private val prub2Label = JLabel("0")
    private val w4Label = JLabel("0")
    private val sh2Label = JLabel("0")
    private val gPrubLabel = JLabel("0")

    fun createAndShowGUI() {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        val panelInput = JPanel(GridLayout(4, 2, 5, 5)).apply {
            border = BorderFactory.createTitledBorder("Вхідні дані")
            add(JLabel("Середньодобова потужність (МВт):")); add(serDPotField)
            add(JLabel("Середнє квадратичне відхилення (МВт):")); add(serKvadrVidField)
            add(JLabel("Зменшити похибку до φ (с):")); add(serKvadrVidZmenField)
            add(JLabel("Вартість електроенергії (грн/кВт*год):")); add(vartistField)
        }

        val panelOutput = JPanel(GridLayout(11, 2, 5, 5)).apply {
            border = BorderFactory.createTitledBorder("Результати")
            add(JLabel("Частка енергії без небалансів (%):")); add(chastkaEnLabel)
            add(JLabel("W1 (МВт*год):")); add(w1Label)
            add(JLabel("Прибуток1 (тис. грн):")); add(prub1Label)
            add(JLabel("W2 (МВт*год):")); add(w2Label)
            add(JLabel("Штраф1 (тис. грн):")); add(sh1Label)
            add(JLabel("Частка енергії після вдосконалення (%):")); add(chastkaEn2Label)
            add(JLabel("W3 (МВт*год):")); add(w3Label)
            add(JLabel("Прибуток2 (тис. грн):")); add(prub2Label)
            add(JLabel("W4 (МВт*год):")); add(w4Label)
            add(JLabel("Штраф2 (тис. грн):")); add(sh2Label)
            add(JLabel("Головний прибуток (тис. грн):")); add(gPrubLabel)
        }

        val calcButton = JButton("Обчислити").apply {
            addActionListener { calculate() }
        }

        val panelMain = JPanel(BorderLayout(10, 10)).apply {
            add(panelInput, BorderLayout.NORTH)
            add(calcButton, BorderLayout.CENTER)
            add(panelOutput, BorderLayout.SOUTH)
        }

        frame.add(panelMain)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }

    private fun calculate() {
        val serDPot = serDPotField.text.toDouble()
        val serKvadrVid = serKvadrVidField.text.toDouble()
        val serKvadrVidZmen = serKvadrVidZmenField.text.toDouble()
        val vartist = vartistField.text.toDouble()

        val chastkaEn1 = chastkaEn(serDPot, serKvadrVid)
        val w1 = w1(serDPot, serKvadrVid) * 0.01
        val w2 = w2(serDPot, serKvadrVid) * 0.01
        val chastkaEn2 = chastkaEn(serDPot, serKvadrVidZmen)
        val w3 = w1(serDPot, serKvadrVidZmen) * 0.01
        val w4 = w2(serDPot, serKvadrVidZmen) * 0.01

        val prub1 = w1 * vartist
        val sh1 = w2 * vartist
        val prub2 = w3 * vartist
        val sh2 = w4 * vartist
        val gPrub = prub2 - sh2

        // Встановлення результатів
        chastkaEnLabel.text = "%.0f".format(chastkaEn1)
        w1Label.text = "%.0f".format(w1)
        prub1Label.text = "%.1f".format(prub1)
        w2Label.text = "%.0f".format(w2)
        sh1Label.text = "%.1f".format(sh1)
        chastkaEn2Label.text = "%.0f".format(chastkaEn2)
        w3Label.text = "%.1f".format(w3)
        prub2Label.text = "%.1f".format(prub2)
        w4Label.text = "%.1f".format(w4)
        sh2Label.text = "%.1f".format(sh2)
        gPrubLabel.text = "%.1f".format(gPrub)
    }

    private fun w1(serDPot: Double, serKvadrVid: Double) = serDPot * 24 * chastkaEn(serDPot, serKvadrVid)
    private fun w2(serDPot: Double, serKvadrVid: Double) = serDPot * 24 * (100 - chastkaEn(serDPot, serKvadrVid))

    private fun chastkaEn(mean: Double, stddev: Double): Double {
        val lower = 4.75
        val upper = 5.25
        return 0.5 * (erf((upper - mean) / (sqrt(2.0) * stddev)) - erf((lower - mean) / (sqrt(2.0) * stddev))) * 100
    }

    private fun erf(x: Double): Double {

        val a1 = 0.254829592
        val a2 = -0.284496736
        val a3 = 1.421413741
        val a4 = -1.453152027
        val a5 = 1.061405429
        val p = 0.3275911

        val sign = if (x >= 0) 1 else -1
        val absX = abs(x)
        val t = 1.0 / (1.0 + p * absX)
        val y = 1.0 - ((((a5 * t + a4) * t + a3) * t + a2) * t + a1) * t * exp(-absX * absX)
        return sign * y
    }
}
