/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { MCOUpdateComponent } from 'app/entities/mco/mco-update.component';
import { MCOService } from 'app/entities/mco/mco.service';
import { MCO } from 'app/shared/model/mco.model';

describe('Component Tests', () => {
    describe('MCO Management Update Component', () => {
        let comp: MCOUpdateComponent;
        let fixture: ComponentFixture<MCOUpdateComponent>;
        let service: MCOService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [MCOUpdateComponent]
            })
                .overrideTemplate(MCOUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MCOUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MCOService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MCO(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mCO = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MCO();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mCO = entity;
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
