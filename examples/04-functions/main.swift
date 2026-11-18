func greeting(for name: String) -> String {
    "Привіт, \(name)!"
}

func minutesToHours(_ minutes: Int) -> Double {
    Double(minutes) / 60.0
}

print(greeting(for: "Володимир"))
print("45 хв = \(minutesToHours(45)) год")
