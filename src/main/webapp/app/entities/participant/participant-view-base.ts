import { Component } from '@angular/core';

/**
 * I wanted a place toact as the 'top level' of the participant view.
 * This component just holds the views and routes of the other components that get displayed alongside of the participant
 * update component
 */

@Component({
    selector: 'jhi-participant-view',
    templateUrl: './participant-view-base.html'
})
export class ParticipantViewBaseComponent {}
