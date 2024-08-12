import { Routes } from '@angular/router';
import { WelcomepageComponent } from './components/welcomepage/welcomepage.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

export const routes: Routes = [
    {path: '', component:WelcomepageComponent},
    {path: 'login', component:LoginComponent},
    {path:'registration', component:RegisterComponent}
];
