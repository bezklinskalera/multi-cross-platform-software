import javax.swing.*
import java.awt.*


fun main() {
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val frame = JFrame("Практична робота №5 - Надійність електропостачальних систем")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(800, 700)
    frame.layout = BorderLayout()

    val panel = JPanel()
    panel.layout = GridLayout(0, 2, 10, 10)

    // --- Вхідні дані ---
    val labelsAndFields = listOf(
        "Частота відмов елегазовий вимикач 110 кВ" to "0.01",
        "Тривалість відновлення елегазовий вимикач 110 кВ" to "30",
        "Частота відмов на 10 км ПЛ-110 кВ" to "0.07",
        "Тривалість відновлення на 10 км ПЛ-110" to "10",
        "Частота відмов трансформатор 110/10 кВ" to "0.015",
        "Тривалість відновлення трансформатор 110/10" to "100",
        "Частота відмов ввідний вимикач 10 кВ" to "0.02",
        "Тривалість відновлення ввідний вимикач 10 кВ" to "15",
        "Частота відмов 6 приєднань 10 кВ" to "0.18",
        "Тривалість відновлення 6 приєднань 10 кВ" to "2",
        "Частота відмов секційного вимикача 10 кВ" to "0.02",
        "Частота відмов (однотрансформаторна ГПП)" to "0.01",
        "Тривалість відновлення (однотрансформаторна ГПП)" to "0.045",
        "Середній час планового простою трансформатора 35 кВ" to "0.004",
        "Питомі збитки аварійні (грн/кВт*год)" to "23.6",
        "Питомі збитки планові (грн/кВт*год)" to "17.6"
    )

    val textFields = mutableMapOf<String, JTextField>()

    for ((labelText, defaultValue) in labelsAndFields) {
        panel.add(JLabel(labelText))
        val tf = JTextField(defaultValue)
        textFields[labelText] = tf
        panel.add(tf)
    }

    val button = JButton("Обчислити")
    panel.add(button)
    panel.add(JLabel("")) // Пустий простір

    val resultLabels = listOf(
        "Частота відмов одноколової системи (рік^-1)",
        "Середня тривалість відновлення (год)",
        "Коефіцієнт аварійного простою",
        "Коефіцієнт планового простою",
        "Частота відмов одночасно двох кіл двоколової системи",
        "Частота відмов двоколової системи з урахуванням секційного вимикача",
        "Математичне сподівання аварійного недовідпущення електроенергії (кВт*год)",
        "Математичне сподівання планового недовідпущення електроенергії (кВт*год)",
        "Математичне сподівання збитків від переривання електропостачання (грн)"
    )

    val resultFields = mutableMapOf<String, JTextField>()
    for (labelText in resultLabels) {
        panel.add(JLabel(labelText))
        val tf = JTextField()
        tf.isEditable = false
        resultFields[labelText] = tf
        panel.add(tf)
    }

    button.addActionListener {

        val chVidEl110 = textFields["Частота відмов елегазовий вимикач 110 кВ"]!!.text.toDouble()
        val trVidEl110 = textFields["Тривалість відновлення елегазовий вимикач 110 кВ"]!!.text.toDouble()
        val chVidPl110 = textFields["Частота відмов на 10 км ПЛ-110 кВ"]!!.text.toDouble()
        val trVidPl110 = textFields["Тривалість відновлення на 10 км ПЛ-110"]!!.text.toDouble()
        val chVidT110 = textFields["Частота відмов трансформатор 110/10 кВ"]!!.text.toDouble()
        val trVidT110 = textFields["Тривалість відновлення трансформатор 110/10"]!!.text.toDouble()
        val chVidVV10 = textFields["Частота відмов ввідний вимикач 10 кВ"]!!.text.toDouble()
        val trVidVV10 = textFields["Тривалість відновлення ввідний вимикач 10 кВ"]!!.text.toDouble()
        val chVidPr10 = textFields["Частота відмов 6 приєднань 10 кВ"]!!.text.toDouble()
        val trVidPr10 = textFields["Тривалість відновлення 6 приєднань 10 кВ"]!!.text.toDouble()
        val chVidSek = textFields["Частота відмов секційного вимикача 10 кВ"]!!.text.toDouble()

        val chVid2 = textFields["Частота відмов (однотрансформаторна ГПП)"]!!.text.toDouble()
        val trVid2 = textFields["Тривалість відновлення (однотрансформаторна ГПП)"]!!.text.toDouble()
        val serChas2 = textFields["Середній час планового простою трансформатора 35 кВ"]!!.text.toDouble()
        val zbutkiAv = textFields["Питомі збитки аварійні (грн/кВт*год)"]!!.text.toDouble()
        val zbutkiPl = textFields["Питомі збитки планові (грн/кВт*год)"]!!.text.toDouble()

        val freqOdnok = chVidEl110 + chVidPl110 + chVidT110 + chVidVV10 + chVidPr10
        val tryvOdnok = (chVidEl110*trVidEl110 + chVidPl110*trVidPl110 + chVidT110*trVidT110 + chVidVV10*trVidVV10 + chVidPr10*trVidPr10)/freqOdnok
        val kAvaVal = freqOdnok * tryvOdnok / 8760
        val kPlanVal = 1.2*(43.0/8760.0)
        val chDvaKolaVal = 2*freqOdnok*(kAvaVal+kPlanVal)
        val chDvaKolaSekVal = chDvaKolaVal + chVidSek
        val matAvVal = chVid2*trVid2*5120*6451
        val matPlVal = serChas2*5120*6451
        val matZbVal = zbutkiAv*matAvVal + zbutkiPl*matPlVal

        resultFields["Частота відмов одноколової системи (рік^-1)"]!!.text = "%.3f".format(freqOdnok)
        resultFields["Середня тривалість відновлення (год)"]!!.text = "%.1f".format(tryvOdnok)
        resultFields["Коефіцієнт аварійного простою"]!!.text = "%.5f".format(kAvaVal)
        resultFields["Коефіцієнт планового простою"]!!.text = "%.5f".format(kPlanVal)
        resultFields["Частота відмов одночасно двох кіл двоколової системи"]!!.text = "%.5f".format(chDvaKolaVal)
        resultFields["Частота відмов двоколової системи з урахуванням секційного вимикача"]!!.text = "%.4f".format(chDvaKolaSekVal)
        resultFields["Математичне сподівання аварійного недовідпущення електроенергії (кВт*год)"]!!.text = "%.0f".format(matAvVal)
        resultFields["Математичне сподівання планового недовідпущення електроенергії (кВт*год)"]!!.text = "%.0f".format(matPlVal)
        resultFields["Математичне сподівання збитків від переривання електропостачання (грн)"]!!.text = "%.0f".format(matZbVal)
    }

    frame.add(JScrollPane(panel), BorderLayout.CENTER)
    frame.isVisible = true
}
