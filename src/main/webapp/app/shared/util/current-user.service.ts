import { Injectable } from '@angular/core';
import { IUser } from 'app/core';

@Injectable({
    providedIn: 'root'
})
export class CurrentUserService {
    currentlyLoggedInUser: IUser;
}
