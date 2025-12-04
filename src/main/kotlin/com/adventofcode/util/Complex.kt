import org.apache.commons.math3.complex.Complex

operator fun Complex.plus(b: Complex): Complex {
    return this.add(b)
}

operator fun Complex.plus(b: Double): Complex {
    return this.add(b)
}

operator fun Complex.minus(b: Complex): Complex {
    return this.subtract(b)
}

operator fun Complex.minus(b: Double): Complex {
    return this.subtract(b)
}

operator fun Complex.times(b: Complex): Complex {
    return this.multiply(b)
}

operator fun Complex.times(b: Double): Complex {
    return this.multiply(b)
}

operator fun Complex.times(b: Int): Complex {
    return this.multiply(b)
}

operator fun Complex.div(b: Complex): Complex {
    return this.divide(b)
}

operator fun Complex.div(b: Double): Complex {
    return this.divide(b)
}
