/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { GoalSettingsUpdateComponent } from 'app/entities/goal-settings/goal-settings-update.component';
import { GoalSettingsService } from 'app/entities/goal-settings/goal-settings.service';
import { GoalSettings } from 'app/shared/model/goal-settings.model';

describe('Component Tests', () => {
    describe('GoalSettings Management Update Component', () => {
        let comp: GoalSettingsUpdateComponent;
        let fixture: ComponentFixture<GoalSettingsUpdateComponent>;
        let service: GoalSettingsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TwentyOnePointsTestModule],
                declarations: [GoalSettingsUpdateComponent]
            })
                .overrideTemplate(GoalSettingsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GoalSettingsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalSettingsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new GoalSettings(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.goalSettings = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new GoalSettings();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.goalSettings = entity;
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
