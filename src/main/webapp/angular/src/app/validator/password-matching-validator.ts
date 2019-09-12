import { ValidatorFn, ValidationErrors, FormGroup } from '@angular/forms';

export const passwordMatchingValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    return control && control.get("password").value !== control.get("matchingPassword").value ? { 'matchingPassword': true } : null;
};