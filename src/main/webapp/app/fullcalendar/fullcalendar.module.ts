import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FullcalendarRoutingModule } from './fullcalendar-routing.module';
import { FullcalendarComponent } from './fullcalendar.component';

@NgModule({
    declarations: [FullcalendarComponent],
    imports: [CommonModule, FullcalendarRoutingModule]
})
export class FullcalendarModule {}
