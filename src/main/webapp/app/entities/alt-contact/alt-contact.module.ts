import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    AltContactComponent,
    AltContactDetailComponent,
    AltContactUpdateComponent,
    AltContactDeletePopupComponent,
    AltContactDeleteDialogComponent,
    altContactRoute,
    altContactPopupRoute
} from './';
import { TextMaskModule } from 'angular2-text-mask';

const ENTITY_STATES = [...altContactRoute, ...altContactPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES), TextMaskModule],
    declarations: [
        AltContactComponent,
        AltContactDetailComponent,
        AltContactUpdateComponent,
        AltContactDeleteDialogComponent,
        AltContactDeletePopupComponent
    ],
    entryComponents: [AltContactComponent, AltContactUpdateComponent, AltContactDeleteDialogComponent, AltContactDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2AltContactModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
