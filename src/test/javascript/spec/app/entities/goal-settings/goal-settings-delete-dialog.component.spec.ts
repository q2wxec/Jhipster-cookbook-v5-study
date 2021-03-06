/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { GoalSettingsDeleteDialogComponent } from 'app/entities/goal-settings/goal-settings-delete-dialog.component';
import { GoalSettingsService } from 'app/entities/goal-settings/goal-settings.service';

describe('Component Tests', () => {
    describe('GoalSettings Management Delete Component', () => {
        let comp: GoalSettingsDeleteDialogComponent;
        let fixture: ComponentFixture<GoalSettingsDeleteDialogComponent>;
        let service: GoalSettingsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TwentyOnePointsTestModule],
                declarations: [GoalSettingsDeleteDialogComponent]
            })
                .overrideTemplate(GoalSettingsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GoalSettingsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalSettingsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
