/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionTypeUpdateComponent } from 'app/entities/action-type/action-type-update.component';
import { ActionTypeService } from 'app/entities/action-type/action-type.service';
import { ActionType } from 'app/shared/model/action-type.model';

describe('Component Tests', () => {
    describe('ActionType Management Update Component', () => {
        let comp: ActionTypeUpdateComponent;
        let fixture: ComponentFixture<ActionTypeUpdateComponent>;
        let service: ActionTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionTypeUpdateComponent]
            })
                .overrideTemplate(ActionTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionType = entity;
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
