import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from 'src/app/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  id:number|undefined;

  userFormGroup = new FormGroup({
    email: new FormControl('',[Validators.email]),
    password: new FormControl('',[Validators.required]),

  });

  ngOnInit(){

  }

  constructor(private authService:AuthServiceService,private router:Router){

  }
 


  error:any
  errorMsg:any

  validateUser(){
    console.log(this.userFormGroup.value);
    let user = this.userFormGroup.value;
    this.authService.checkIsValidUser(user).subscribe({
        next:()=>{
          console.log("Logged in Successfully")
        },
        error:(err:any)=>{
          console.log(err);
          this.error = true;
          console.log(err.error.value)
          this.errorMsg = err.error.value;
          alert(this.errorMsg);
        }
      });
      // console.log(this.studentServ.getStudent);
      this.userFormGroup.reset;
      this.router.navigate(['/category']);
    
  }

  goToForgotPassword() {
    this.router.navigate(['/forgot-password']);
  }
  
  getFormControl(name:string){
    return this.userFormGroup.get(name);
  }

  isFormControlError(name:string){
    return this.getFormControl(name)?.dirty && this.getFormControl(name)?.invalid && this.getFormControl(name)?.errors?.['required']
  }

}
