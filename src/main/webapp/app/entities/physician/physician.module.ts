import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    PhysicianComponent,
    PhysicianDetailComponent,
    PhysicianUpdateComponent,
    PhysicianDeletePopupComponent,
    PhysicianDeleteDialogComponent,
    physicianRoute,
    physicianPopupRoute
} from './';

const ENTITY_STATES = [...physicianRoute, ...physicianPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PhysicianComponent,
        PhysicianDetailComponent,
        PhysicianUpdateComponent,
        PhysicianDeleteDialogComponent,
        PhysicianDeletePopupComponent
    ],
    entryComponents: [PhysicianComponent, PhysicianUpdateComponent, PhysicianDeleteDialogComponent, PhysicianDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2PhysicianModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
