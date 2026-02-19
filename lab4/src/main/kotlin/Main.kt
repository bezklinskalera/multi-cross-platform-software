import javax.swing.*
import java.awt.*
import kotlin.math.sqrt
import kotlin.math.pow

fun main() {

    val frame = JFrame("Практична робота №4")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(1200, 800)
    frame.layout = BorderLayout()

    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
    frame.add(JScrollPane(panel), BorderLayout.CENTER)

    fun createInputField(labelText: String, defaultValue: Double): JTextField {
        val subPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        val label = JLabel(labelText)
        val textField = JTextField(defaultValue.toString(), 8)
        subPanel.add(label)
        subPanel.add(textField)
        panel.add(subPanel)
        return textField
    }

    // Завдання 1
    val strymKZField = createInputField("Струм КЗ Iк (kA):", 2.5)
    val naprygaField = createInputField("Напруга U (кВ):", 10.0)
    val fictTimeKZField = createInputField("Фіктивний час вимкнення tф (с):", 2.5)
    val potTPField = createInputField("Потужність ТП (кВА):", 2000.0)
    val rozNavField = createInputField("Розрахункове навантаження Sм (кВА):", 1300.0)
    val tmField = createInputField("Tм (год):", 4000.0)

    // Завдання 2
    val potKZ2Field = createInputField("Потужність КЗ (МВA):", 200.0)
    val napruga2Field = createInputField("Напруга 2 (кВ):", 10.5)
    val sNomt2Field = createInputField("S номт2 (кВА):", 6.3)

    // Завдання 3
    val rSn3Field = createInputField("Rс.н (Ом):", 10.65)
    val xSn3Field = createInputField("Xс.н (Ом):", 24.02)
    val rSmin3Field = createInputField("Rс.min (Ом):", 34.88)
    val xSmin3Field = createInputField("Xс.min (Ом):", 65.68)
    val umax3Field = createInputField("Uк.max (%):", 11.1)
    val uVn3Field = createInputField("Uв.н (%):", 115.0)

    val ll3Field = createInputField("lл (км):", 12.37)
    val rl0Field = createInputField("R0 (Ом):", 0.64)
    val xl0Field = createInputField("X0 (Ом):", 0.363)


    fun createOutputLabel(labelText: String): JLabel {
        val subPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        val label = JLabel(labelText)
        val output = JLabel("")
        output.preferredSize = Dimension(100, 20)
        subPanel.add(label)
        subPanel.add(output)
        panel.add(subPanel)
        return output
    }

    val rozStrymNormAvLabel = createOutputLabel("Розрахунковий струм нормальний режим:")
    val rozStrymAvLabel = createOutputLabel("Розрахунковий струм аварійний режим:")
    val ecoPererLabel = createOutputLabel("Економічний переріз sек (мм²):")
    val ssLabel = createOutputLabel("s≥smin (мм²):")

    val xCLabel = createOutputLabel("Опір XC (Ом):")
    val xTLabel = createOutputLabel("Опір XT (Ом):")
    val sumaOpirLabel = createOutputLabel("Сумарний опір (Ом):")
    val pochStrymLabel = createOutputLabel("Початкове діюче значення струму (А):")

    val calculateBtn = JButton("Обчислити")
    panel.add(calculateBtn)

    calculateBtn.addActionListener {

        val strymKZ = strymKZField.text.toDouble()
        val napryga = naprygaField.text.toDouble()
        val fictTimeKZ = fictTimeKZField.text.toDouble()
        val potTP = potTPField.text.toDouble()
        val rozNav = rozNavField.text.toDouble()
        val tm = tmField.text.toDouble()

        val potKZ2 = potKZ2Field.text.toDouble()
        val napruga2 = napruga2Field.text.toDouble()
        val sNomt2 = sNomt2Field.text.toDouble()

        val rSn3 = rSn3Field.text.toDouble()
        val xSn3 = xSn3Field.text.toDouble()
        val rSmin3 = rSmin3Field.text.toDouble()
        val xSmin3 = xSmin3Field.text.toDouble()
        val umax3 = umax3Field.text.toDouble()
        val uVn3 = uVn3Field.text.toDouble()

        val ll3 = ll3Field.text.toDouble()
        val rl0 = rl0Field.text.toDouble()
        val xl0 = xl0Field.text.toDouble()

        fun rozStrymNormAv(rozNav: Double, Napryga: Double) = (rozNav/2)/(sqrt(3.0)*Napryga)
        fun rozStrymAv(rozNav: Double, Napryga: Double) = 2*((rozNav/2)/(sqrt(3.0)*Napryga))
        fun ecoPerer(roz: Double) = roz/1.4
        fun ss(strymKZ: Double, t: Double) = (strymKZ*sqrt(t))/92
        fun OpirXc(U: Double, P: Double) = U.pow(2)/P
        fun OpirXt(U: Double, S: Double) = (U/100)*(U.pow(2)/S)
        fun sumaOpir(U: Double, P: Double, S: Double) = OpirXc(U,P) + OpirXt(U,S)
        fun pochStrym(U: Double, P: Double, S: Double) = U/(sqrt(3.0)*sumaOpir(U,P,S))


        val rozNorm = rozStrymNormAv(rozNav, napryga)
        val rozAv = rozStrymAv(rozNav, napryga)
        val eco = ecoPerer(rozNorm)
        val ssVal = ss(strymKZ, fictTimeKZ)
        val xC = OpirXc(napruga2, potKZ2)
        val xT = OpirXt(napruga2, sNomt2)
        val sumaOpirVal = sumaOpir(napruga2, potKZ2, sNomt2)
        val pochStrymVal = pochStrym(napruga2, potKZ2, sNomt2)


        rozStrymNormAvLabel.text = "%.2f".format(rozNorm)
        rozStrymAvLabel.text = "%.2f".format(rozAv)
        ecoPererLabel.text = "%.2f".format(eco)
        ssLabel.text = "%.2f".format(ssVal)
        xCLabel.text = "%.2f".format(xC)
        xTLabel.text = "%.2f".format(xT)
        sumaOpirLabel.text = "%.2f".format(sumaOpirVal)
        pochStrymLabel.text = "%.2f".format(pochStrymVal)
    }

    frame.isVisible = true
}
