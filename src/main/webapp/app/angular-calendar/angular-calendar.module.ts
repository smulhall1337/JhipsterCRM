import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AngularCalendarRoutingModule } from './angular-calendar-routing.module';
import { AngularCalendarComponent } from './angular-calendar.component';

@NgModule({
    declarations: [AngularCalendarComponent],
    imports: [CommonModule, AngularCalendarRoutingModule]
})
export class AngularCalendarModule {}
