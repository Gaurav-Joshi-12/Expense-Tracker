import { Component } from '@angular/core';
import { EmailValidator, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from 'src/app/auth-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {


  // int userId;
  // String name;
  // String username;
  // String password;
  // String email;
  // String mobileNo;
  // int activeYn;

  id:number|undefined;

  userFormGroup = new FormGroup({
    name: new FormControl('',[Validators.required]),
    username: new FormControl('',[Validators.required]),
    password: new FormControl('',[Validators.required]),
    email: new FormControl('',[Validators.required,Validators.email]),
    mobileNo: new FormControl('',[Validators.required]),


  });

  ngOnInit(){

  }

  constructor(private authService:AuthServiceService) {
    
  }

  error:any
  errorMsg:any

  validateUser(){
    console.log(this.userFormGroup.value);
    let user = this.userFormGroup.value;
  }

  submitUser(){
    console.log(this.userFormGroup.value);
    let value = this.userFormGroup.value;
    this.authService.addUser(value).subscribe({
        next:()=>{
          console.log("Registered Successfully")
          alert("User registered");
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
    }
  
  
  getFormControl(name:string){
    return this.userFormGroup.get(name);
  }

  isFormControlError(name:string){
    return this.getFormControl(name)?.dirty && this.getFormControl(name)?.invalid && this.getFormControl(name)?.errors?.['required']
  }
}
