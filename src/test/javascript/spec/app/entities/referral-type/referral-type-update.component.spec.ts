/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ReferralTypeUpdateComponent } from 'app/entities/referral-type/referral-type-update.component';
import { ReferralTypeService } from 'app/entities/referral-type/referral-type.service';
import { ReferralType } from 'app/shared/model/referral-type.model';

describe('Component Tests', () => {
    describe('ReferralType Management Update Component', () => {
        let comp: ReferralTypeUpdateComponent;
        let fixture: ComponentFixture<ReferralTypeUpdateComponent>;
        let service: ReferralTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ReferralTypeUpdateComponent]
            })
                .overrideTemplate(ReferralTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReferralTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferralTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferralType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referralType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferralType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referralType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
