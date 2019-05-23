import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ContactSubTypeComponent,
    ContactSubTypeDetailComponent,
    ContactSubTypeUpdateComponent,
    ContactSubTypeDeletePopupComponent,
    ContactSubTypeDeleteDialogComponent,
    contactSubTypeRoute,
    contactSubTypePopupRoute
} from './';

const ENTITY_STATES = [...contactSubTypeRoute, ...contactSubTypePopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactSubTypeComponent,
        ContactSubTypeDetailComponent,
        ContactSubTypeUpdateComponent,
        ContactSubTypeDeleteDialogComponent,
        ContactSubTypeDeletePopupComponent
    ],
    entryComponents: [
        ContactSubTypeComponent,
        ContactSubTypeUpdateComponent,
        ContactSubTypeDeleteDialogComponent,
        ContactSubTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ContactSubTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
