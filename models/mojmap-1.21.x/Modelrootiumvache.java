// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelrootiumvache<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "rootiumvache"), "main");
	private final ModelPart vache;
	private final ModelPart patte;
	private final ModelPart corps;
	private final ModelPart tete;

	public Modelrootiumvache(ModelPart root) {
		this.vache = root.getChild("vache");
		this.patte = this.vache.getChild("patte");
		this.corps = this.patte.getChild("corps");
		this.tete = this.corps.getChild("tete");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition vache = partdefinition.addOrReplaceChild("vache", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition patte = vache.addOrReplaceChild("patte",
				CubeListBuilder.create().texOffs(32, 30)
						.addBox(-11.0F, -8.0F, 14.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(32, 41)
						.addBox(-11.0F, -8.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(44, 30)
						.addBox(-1.0F, -8.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(44, 41)
						.addBox(-1.0F, -8.0F, 14.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, 0.0F, -6.0F));

		PartDefinition corps = patte.addOrReplaceChild("corps", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F,
				-19.0F, -18.0F, 13.0F, 11.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition tete = corps.addOrReplaceChild("tete",
				CubeListBuilder.create().texOffs(8, 2)
						.addBox(-6.0F, -26.0F, 13.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
						.addBox(-5.0F, -25.0F, 8.0F, 9.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(2, 2)
						.addBox(3.0F, -26.0F, 13.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-4.0F, 0.0F, -10.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		vache.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}