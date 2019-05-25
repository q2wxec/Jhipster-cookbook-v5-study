/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TwentyOnePointsTestModule } from '../../../test.module';
import { GoalSettingsDetailComponent } from 'app/entities/goal-settings/goal-settings-detail.component';
import { GoalSettings } from 'app/shared/model/goal-settings.model';

describe('Component Tests', () => {
    describe('GoalSettings Management Detail Component', () => {
        let comp: GoalSettingsDetailComponent;
        let fixture: ComponentFixture<GoalSettingsDetailComponent>;
        const route = ({ data: of({ goalSettings: new GoalSettings(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TwentyOnePointsTestModule],
                declarations: [GoalSettingsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GoalSettingsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GoalSettingsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.goalSettings).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
