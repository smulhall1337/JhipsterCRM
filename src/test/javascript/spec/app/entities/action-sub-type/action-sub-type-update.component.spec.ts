/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionSubTypeUpdateComponent } from 'app/entities/action-sub-type/action-sub-type-update.component';
import { ActionSubTypeService } from 'app/entities/action-sub-type/action-sub-type.service';
import { ActionSubType } from 'app/shared/model/action-sub-type.model';

describe('Component Tests', () => {
    describe('ActionSubType Management Update Component', () => {
        let comp: ActionSubTypeUpdateComponent;
        let fixture: ComponentFixture<ActionSubTypeUpdateComponent>;
        let service: ActionSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionSubTypeUpdateComponent]
            })
                .overrideTemplate(ActionSubTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionSubTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionSubTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionSubType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionSubType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ActionSubType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.actionSubType = entity;
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
