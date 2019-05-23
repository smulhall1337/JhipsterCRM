/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralTypeComponent } from 'app/entities/referral-type/referral-type.component';
import { ReferralTypeService } from 'app/entities/referral-type/referral-type.service';
import { ReferralType } from 'app/shared/model/referral-type.model';

describe('Component Tests', () => {
    describe('ReferralType Management Component', () => {
        let comp: ReferralTypeComponent;
        let fixture: ComponentFixture<ReferralTypeComponent>;
        let service: ReferralTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralTypeComponent],
                providers: []
            })
                .overrideTemplate(ReferralTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReferralTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferralTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReferralType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.referralTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
