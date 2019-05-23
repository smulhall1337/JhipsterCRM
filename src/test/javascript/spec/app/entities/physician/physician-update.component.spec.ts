/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { PhysicianUpdateComponent } from 'app/entities/physician/physician-update.component';
import { PhysicianService } from 'app/entities/physician/physician.service';
import { Physician } from 'app/shared/model/physician.model';

describe('Component Tests', () => {
    describe('Physician Management Update Component', () => {
        let comp: PhysicianUpdateComponent;
        let fixture: ComponentFixture<PhysicianUpdateComponent>;
        let service: PhysicianService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [PhysicianUpdateComponent]
            })
                .overrideTemplate(PhysicianUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PhysicianUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhysicianService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Physician(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.physician = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Physician();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.physician = entity;
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
