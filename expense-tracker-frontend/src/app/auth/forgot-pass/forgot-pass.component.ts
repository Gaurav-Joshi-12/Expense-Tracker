import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthServiceService } from 'src/app/auth-service.service';
@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['./forgot-pass.component.css']
})

export class ForgotPassComponent {
  
  forgotFormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email])
  });
  

  constructor(private authService:AuthServiceService,private router:Router){

  }

  ngOnInit(): void {
    
  }

  isFormControlError(controlName: string): boolean {
    const control = this.forgotFormGroup.get(controlName);
    return !!(control && control.invalid && (control.touched || control.dirty));
  }

  // sendResetLink(): void {
  //   if (this.forgotFormGroup.valid) {
  //     console.log(this.forgotFormGroup.value);
  //     this.authService.resetPassword(this.forgotFormGroup.value).subscribe(
  //       {
  //         next: () => {
  //           console.log('Reset email sent successfully');
  //         },
  //         error: (err) => {
  //           console.error('Error sending mail', err);
  //         }
  //       }
  //     );
  //   }

  // }

  sendResetLink(): void {
    if (this.forgotFormGroup.valid) {
      const email = this.forgotFormGroup.get('email')?.value;
  
      this.authService.resetPassword(email).subscribe({
        next: () => {
          console.log('Reset email sent successfully');
        },
        error: (err) => {
          alert('Error sending mail');
        }
      });
    }
  }
  

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
