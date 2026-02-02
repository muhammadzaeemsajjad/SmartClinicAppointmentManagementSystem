package CustomExceptions;
public class ValidationUtil {

    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        return address.trim();
    }

    public static int validateExperienceYears(String yearsStr) {
        try {
            int years = Integer.parseInt(yearsStr);
            if (years < 0 || years > 80) {
                throw new IllegalArgumentException("Experience years must be between 0 and 80.");
            }
            return years;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Experience years must be a valid number.");
        }
    }

    public static int validateID(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                throw new IllegalArgumentException("ID must be a positive number.");
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a valid number.");
        }
    }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                throw new IllegalArgumentException("Name must contain only letters and spaces.");
            }
        }
        return name.trim();
    }

    public static int validateAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 1 || age > 120) {
                throw new IllegalArgumentException("Age must be between 1 and 120.");
            }
            return age;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid number.");
        }
    }

    public static String validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        return email.trim();
    }

    public static String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty.");
        }

        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Phone must contain digits only.");
            }
        }

        if (phone.length() != 11) {
            throw new IllegalArgumentException("Phone number must be 11 digits.");
        }

        return phone;
    }

    public static String validateCNIC(String cnic) {
    if (cnic == null || cnic.trim().isEmpty()) {
        throw new IllegalArgumentException("CNIC cannot be empty.");
    }

    cnic = cnic.trim();

    // Must be exactly 13 digits
    if (cnic.length() != 13) {
        throw new IllegalArgumentException("CNIC must be exactly 13 digits.");
    }

    // Check all characters are digits
    for (char c : cnic.toCharArray()) {
        if (!Character.isDigit(c)) {
            throw new IllegalArgumentException("CNIC must contain digits only.");
        }
    }

    // Optional: CNIC should not start with 0
    if (cnic.charAt(0) == '0') {
        throw new IllegalArgumentException("CNIC cannot start with 0.");
    }

    return cnic;
}

    public static String validateDateTime(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            throw new IllegalArgumentException("DateTime cannot be empty.");
        }
      
        return dateTime.trim();
    }


}
