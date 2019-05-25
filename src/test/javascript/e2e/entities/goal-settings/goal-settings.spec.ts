/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GoalSettingsComponentsPage, GoalSettingsDeleteDialog, GoalSettingsUpdatePage } from './goal-settings.page-object';

const expect = chai.expect;

describe('GoalSettings e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let goalSettingsUpdatePage: GoalSettingsUpdatePage;
    let goalSettingsComponentsPage: GoalSettingsComponentsPage;
    let goalSettingsDeleteDialog: GoalSettingsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load GoalSettings', async () => {
        await navBarPage.goToEntity('goal-settings');
        goalSettingsComponentsPage = new GoalSettingsComponentsPage();
        await browser.wait(ec.visibilityOf(goalSettingsComponentsPage.title), 5000);
        expect(await goalSettingsComponentsPage.getTitle()).to.eq('twentyOnePointsApp.goalSettings.home.title');
    });

    it('should load create GoalSettings page', async () => {
        await goalSettingsComponentsPage.clickOnCreateButton();
        goalSettingsUpdatePage = new GoalSettingsUpdatePage();
        expect(await goalSettingsUpdatePage.getPageTitle()).to.eq('twentyOnePointsApp.goalSettings.home.createOrEditLabel');
        await goalSettingsUpdatePage.cancel();
    });

    it('should create and save GoalSettings', async () => {
        const nbButtonsBeforeCreate = await goalSettingsComponentsPage.countDeleteButtons();

        await goalSettingsComponentsPage.clickOnCreateButton();
        await promise.all([
            goalSettingsUpdatePage.setWeeklyGoalInput('11'),
            goalSettingsUpdatePage.weightUnitsSelectLastOption(),
            goalSettingsUpdatePage.userSelectLastOption()
        ]);
        expect(await goalSettingsUpdatePage.getWeeklyGoalInput()).to.eq('11');
        await goalSettingsUpdatePage.save();
        expect(await goalSettingsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await goalSettingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last GoalSettings', async () => {
        const nbButtonsBeforeDelete = await goalSettingsComponentsPage.countDeleteButtons();
        await goalSettingsComponentsPage.clickOnLastDeleteButton();

        goalSettingsDeleteDialog = new GoalSettingsDeleteDialog();
        expect(await goalSettingsDeleteDialog.getDialogTitle()).to.eq('twentyOnePointsApp.goalSettings.delete.question');
        await goalSettingsDeleteDialog.clickOnConfirmButton();

        expect(await goalSettingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
