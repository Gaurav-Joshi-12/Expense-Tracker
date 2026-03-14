import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthServiceService } from 'src/app/auth-service.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  

  constructor( private activatedRoute:ActivatedRoute,private authService:AuthServiceService,private router:Router){}

  resetPwdGroup = new FormGroup({
    newPassword : new FormControl('',[Validators.required,Validators.minLength(6)]),
    confirmPassword : new FormControl('',[Validators.required]), 
  })

  isFormControlError(controlName: string){
    const control = this.resetPwdGroup.get(controlName);
    return control && control.invalid && (control.dirty || control.touched);
  }

  isPasswordMatches(){
    return this.resetPwdGroup.get('newPassword')?.value === this.resetPwdGroup.get('confirmPassword')?.value
  }


  token:any;
  ngOnInit(){
    this.token = this.activatedRoute.snapshot.paramMap.get("token");
    console.log(this.token);
    // this.authService.validateToken(this.token).subscribe(()=>{
    //   error:(err:any)=>{
    //     alert("Token is invalid")
    //     this.router.navigate(["/login"])
    //   }
    // })
  }

  // resetPassword(){
  //   const data = {
  //     newPassword:this.resetPwdGroup.get("newPassword")?.value,
  //     token:this.token

  //   }
  //   this.authService.resetPassToken(data).subscribe(()=>{
  //     error:(err:any)=>{
  //       alert("Token is invalid")
  //       this.router.navigate(["/login"])
  //     }
  //   })
  // }

  resetPassword() {
    const payload = {
      token: this.token,
      newPassword: this.resetPwdGroup.get("newPassword")?.value
    };
  
    this.authService.resetPassToken(payload).subscribe({
      next: () => {
        console.log("Password reset successful");
        this.router.navigate(["/login"]);
      }
    });
  }
  
}
